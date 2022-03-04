package ru.instamart.api.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.common.ProductsFilterParams;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.request.v2.*;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.util.CollectionUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.ThreadUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.fail;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.common.RestStaticTestData.userPhone;
import static ru.instamart.kraken.util.ThreadUtil.simplyAwait;

@Slf4j
public final class ApiV2Helper {

    private final ThreadLocal<Integer> currentSid = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentAddressId = new ThreadLocal<>();
    private final ThreadLocal<String> currentOrderNumber = new ThreadLocal<>();
    private final ThreadLocal<String> currentShipmentNumber = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentShipmentId = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentDeliveryWindowId = new ThreadLocal<>();
    private final ThreadLocal<PaymentToolV2> currentPaymentTool = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentShipmentMethodId = new ThreadLocal<>();
    private final ThreadLocal<Integer> minSum = new ThreadLocal<>();
    private final ThreadLocal<Boolean> minSumNotReached = new ThreadLocal<>();
    private final ThreadLocal<Boolean> productWeightNotDefined = new ThreadLocal<>();
    private final ThreadLocal<Boolean> orderCompleted = new ThreadLocal<>();

    /**
     * Округляем double до определенного количества символов после запятой
     */
    private double roundBigDecimal(double value, int decimal) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(decimal, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Проверяем, попадают ли координаты в зону доставки
     */
    private boolean isPointInPolygon(List<ZoneV2> points, ZoneV2 point) {
        int i, j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if (((points.get(i).getLon() > point.getLon()) != (points.get(j).getLon() > point.getLon())) &&
                    (point.getLat() < (points.get(j).getLat() - points.get(i).getLat())
                            * (point.getLon() - points.get(i).getLon())
                            / (points.get(j).getLon() - points.get(i).getLon())
                            + points.get(i).getLat()))
                result = !result;
        }
        return result;
    }

    /**
     * Получаем координаты, которые попадают в зону доставки
     */
    public ZoneV2 getInnerPoint(List<ZoneV2> points) {
        List<Double> lats = new ArrayList<>();
        List<Double> lons = new ArrayList<>();

        points.forEach(point -> {
            lats.add(point.getLat());
            lons.add(point.getLon());
        });
        int numberOfTries = 1000;
        double lat = Collections.min(lats);
        double lon = Collections.min(lons);
        double stepLat = (Collections.max(lats) - lat) / numberOfTries;
        double stepLon = (Collections.max(lons) - lon) / numberOfTries;

        ZoneV2 point = new ZoneV2(lat, lon);
        for (int i = 0; !isPointInPolygon(points, point) && i < numberOfTries; i++) {
            lat = roundBigDecimal(lat + stepLat, 7);
            lon = roundBigDecimal(lon + stepLon, 7);

            point.setLat(lat);
            point.setLon(lon);
        }
        return point;
    }

    /**
     * Пропускаем тест, если доступен только самовывоз
     * ToDo убрать хардкод данных
     * ToDo если самовывоз, не скипать тест, а оформлять заказ через сайт
     */
    public void skipTestIfOnlyPickupIsAvailable(StoreV2 store, String zoneName) {
        List<Integer> pickupRetailers = new ArrayList<>();
        pickupRetailers.add(
                53 //провино
        );
        HashMap<Integer, String> pickupZones = new HashMap<>();
        pickupZones.put(
                104, "Зона #4" //METRO, Воронеж, Семилуки Займище
        );
        if (pickupRetailers.contains(store.getRetailer().getId()) ||
                CollectionUtil.hasPairInMap(store.getId(), zoneName, pickupZones)) {
            throw new SkipException("Доступен только самовывоз\nsid: " + store.getId() + "\n" + zoneName);
        }
    }

    public void skipTestIfOnlyPickupIsAvailable(StoreV2 store) {
        skipTestIfOnlyPickupIsAvailable(store, null);
    }

    @Step("Отправляем запрос на получение смс с кодом")
    public PhoneTokenV2 sendSMS(String encryptedPhone) {
        log.debug("Отправляем запрос на получение смс с кодом");
        Response response = PhoneConfirmationsV2Request.POST(encryptedPhone);

        if (response.statusCode() == 422) {
            String errorMessage = response.as(ErrorResponse.class).getErrors().getBase();
            if (errorMessage.startsWith("До повторной отправки:")) {
                log.error(errorMessage);
                ThreadUtil.simplyAwait(StringUtil.extractNumberFromString(errorMessage) + 1);
                response = PhoneConfirmationsV2Request.POST(encryptedPhone);
            }
        }
        checkStatusCode200(response);
        return response.as(PhoneTokenV2Response.class).getPhoneToken();
    }

    @Step("Подтверждаем номер телефона {phone} кодом {code}")
    public SessionV2 confirmPhone(String phone, String code, boolean promoTermsAccepted) {
        log.debug("Подтверждаем номер телефона {} кодом {}", phone, code);
        Response response = PhoneConfirmationsV2Request.PUT(phone, code, promoTermsAccepted);

        checkStatusCode200(response);
        SessionV2 session = response.as(SessionsV2Response.class).getSession();
        log.debug("Получен токен: " + session.getAccessToken());
        return session;
    }

    public List<TaxonV2> getTaxons(int sid) {
        Response response = TaxonsV2Request.GET(sid);
        checkStatusCode200(response);
        return response.as(TaxonsV2Response.class).getTaxons();
    }

    public TaxonV2 getTaxon(int id, int sid) {
        Response response = TaxonsV2Request.GET(id, sid);
        checkStatusCode200(response);
        return response.as(TaxonV2Response.class).getTaxon();
    }

    public Set<Integer> getTaxonIds(int sid) {
        return getTaxons(sid)
                .stream()
                .map(TaxonV2::getChildren)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(TaxonV2::getId)
                .collect(Collectors.toSet());
    }

    /**
     * Удаляем товары из корзины
     */
    @Step("Удаляем товары из корзины")
    public OrderV2 deleteAllShipments() {
        if (currentOrderNumber.get() == null) fail("Номер текущего заказа null");

        Response response = OrdersV2Request.Shipments.DELETE(currentOrderNumber.get());
        checkStatusCode200(response);

        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        log.debug("Удалены все доставки у заказа: {}", order.getNumber());
        return order;
    }

    /**
     * Изменение/применение параметров адреса из объекта адреса с указанием имени и фамилии юзера
     */
    @Step("Изменение/применение параметров адреса из объекта адреса с указанием имени и фамилии юзера")
    public AddressV2 setAddressAttributes(UserData user, AddressV2 address) {
        address.setFirstName(user.getFirstName());
        address.setLastName(user.getLastName());

        return setAddressAttributes(address);
    }

    /**
     * Изменение/применение параметров адреса из объекта адреса
     */
    @Step("Изменение/применение параметров адреса из объекта адреса")
    public AddressV2 setAddressAttributes(AddressV2 address) {
        Response response = OrdersV2Request.ShipAddressChange.PUT(address, currentOrderNumber.get());
        checkStatusCode200(response);
        AddressV2 addressFromResponse = response
                .as(ShipAddressChangeV2Response.class)
                .getShipAddressChange()
                .getOrder()
                .getAddress();
        currentAddressId.set(addressFromResponse.getId());

        log.debug("Применен адрес: {}", addressFromResponse);
        return addressFromResponse;
    }

    @Step("Получение лайн айтемов в заказе {orderNumber}")
    public List<LineItemV2> getLineItems(String orderNumber) {
        Response response = OrdersV2Request.LineItems.GET(orderNumber);
        checkStatusCode200(response);
        return response.as(LineItemsV2Response.class).getLineItems();
    }

    public List<ProductV2> getProducts(int sid) {
        return getProducts(sid, "");
    }

    @Step("Поиск продуктов {query} в {sid} магазине")
    public List<ProductV2> getProducts(int sid, String query) {
        Response response = ProductsV2Request.GET(ProductsFilterParams.builder().sid(sid).query(query).build());
        checkStatusCode200(response);
        return response.as(ProductsV2Response.class).getProducts();
    }

    @Step("Поиск продуктов {priceType.value} в {sid} магазине")
    public List<ProductV2> getProducts(int sid, ProductPriceTypeV2 priceType) {
        switch (priceType) {
            case PER_ITEM:
                return getProducts(sid, "хлеб")
                        .stream()
                        .filter(product -> product.getPriceType().equals(ProductPriceTypeV2.PER_ITEM.getValue()))
                        .collect(Collectors.toList());
            case PER_KILO:
                return getProducts(sid, "яблоки")
                        .stream()
                        .filter(product -> product.getPriceType().equals(ProductPriceTypeV2.PER_KILO.getValue()))
                        .collect(Collectors.toList());
            case PER_PACK:
                return getProducts(sid, "вода")
                        .stream()
                        .filter(product -> product.getPriceType().equals(ProductPriceTypeV2.PER_PACK.getValue()))
                        .collect(Collectors.toList());
            case PER_PACKAGE:
                return getProducts(sid, "колбаса")
                        .stream()
                        .filter(product -> product.getPriceType().equals(ProductPriceTypeV2.PER_PACKAGE.getValue()))
                        .collect(Collectors.toList());
            default: throw new SkipException("Указанный price_type не поддерживается");
        }
    }

    /**
     * Получаем список продуктов: по одному из каждой категории
     */
    @Deprecated
    @Step("Получаем список продуктов: по одному из каждой категории для магазина sid = {sid}")
    public List<ProductV2> getProductFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 1, new SoftAssert());
    }

    /**
     * Получаем список продуктов: максимум (6) из каждой категории
     */
    @Deprecated
    @Step("Получаем список продуктов: максимум (6) из каждой категории для магазина sid = {sid}")
    public List<ProductV2> getProductsFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 6, new SoftAssert());
    }

    /**
     * Получаем список продуктов: максимум (6) из каждой категории и проверяем корректность категорий
     */
    @Deprecated
    public List<ProductV2> getProductsFromEachDepartmentInStore(int sid, SoftAssert softAssert) {
        return getProductsFromEachDepartmentInStore(sid, 6, softAssert);
    }

    /**
     * Получаем список продуктов
     *
     * @param sid                                сид магазина
     * @param numberOfProductsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    @Deprecated
    private List<ProductV2> getProductsFromEachDepartmentInStore(int sid,
                                                                 int numberOfProductsFromEachDepartment,
                                                                 SoftAssert softAssert) {
        Response response = DepartmentsV2Request.GET(sid, numberOfProductsFromEachDepartment);
        checkStatusCode200(response);
        List<DepartmentV2> departments = response.as(DepartmentsV2Response.class).getDepartments().stream()
                .filter(d -> !d.getName().equals("Алкоголь")).collect(Collectors.toList());

        String storeUrl = EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + "?sid=" + sid;

        checkFieldIsNotEmpty(departments, "товары для магазина\n" + storeUrl);

        List<ProductV2> products = new ArrayList<>();

        for (DepartmentV2 department : departments) {
            List<ProductV2> productsFromDepartment = department.getProducts();
            if (productsFromDepartment == null) {
                softAssert.fail("\nПоказывается пустая категория " + department.getName() + "\n" + storeUrl);
                continue;
            }
            if (department.getProductsCount() <= 6)
                softAssert.assertEquals(productsFromDepartment.size(), department.getProductsCount(),
                        "\nВ категории " + department.getName() + " отображается "
                                + productsFromDepartment.size() + " товаров, в products_count указано "
                                + department.getProductsCount() + "\n" + storeUrl);

            StringJoiner productsString = new StringJoiner(
                    "\n• ",
                    "Категория: " + department.getName() + "\n• ",
                    "");
            for (ProductV2 productFromDepartment : productsFromDepartment) {
                products.add(productFromDepartment);
                productsString.add(productFromDepartment.toString());
            }
            log.debug(productsString.toString());
        }
        return products;
    }

    @Step("Получаем список избранных продуктов: для магазина sid = {sid}")
    public List<ProductV2> getProductsFromFavorites(final int sid) {
        var response = FavoritesV2Request.GET(sid);
        checkStatusCode200(response);

        return response.as(FavoritesListItemsV2Response.class)
                .getItems()
                .stream()
                .map(ItemV2::getProduct)
                .collect(Collectors.toList());
    }

    /**
     * Добавляем товар в корзину
     */
    @Step("Добавляем товар в корзину: id товара = {productId} и количество = {quantity} ")
    public LineItemV2 addItemToCart(long productId, int quantity) {
        Response response = LineItemsV2Request.POST(productId, quantity, currentOrderNumber.get());
        checkStatusCode200(response);
        LineItemV2 lineItem = response.as(LineItemV2Response.class).getLineItem();

        log.debug(lineItem.toString());
        return lineItem;
    }

    @Step("Получаем минимальную сумму корзины")
    public int getMinOrderAmount(int sid) {
        double minSum = getStore(sid).getMinOrderAmount();
        Allure.step("Минимальная сумма корзины: " + minSum);
        log.debug("Минимальная сумма корзины: {}", minSum);
        this.minSum.set((int) minSum);
        return (int) minSum;
    }

    @Step("Получаем минимальную сумму корзины")
    public int getMinFirstOrderAmount(int sid) {
        double minSum = getStore(sid).getMinFirstOrderAmount();
        Allure.step("Минимальная сумма корзины: " + minSum);
        log.debug("Минимальная сумма корзины: {}", minSum);
        this.minSum.set((int) minSum);
        return (int) minSum;
    }

    /**
     * Получаем id и номер шипмента,
     * Получаем минимальную сумму заказа, если сумма не набрана
     */
    @Step("Узнаём минимальную сумму заказа, если сумма не набрана")
    private void getMinSumFromAlert() {
        final Response response = OrdersV2Request.Current.GET();
        checkStatusCode200(response);
        final List<ShipmentV2> shipments = response
                .as(OrderV2Response.class)
                .getOrder()
                .getShipments();

        shipments.forEach(shipment -> {
            if (!shipment.getAlerts().isEmpty()) {
                String alertMessage = shipment.getAlerts().get(0).getMessage().replaceAll("[^0-9]", "");
                minSum.set(Integer.parseInt(alertMessage.substring(0, alertMessage.length() - 2)));
                log.debug("Минимальная сумма корзины: {}", minSum.get());
                minSumNotReached.set(true);
            } else {
                log.warn("Минимальная сумма корзины набрана");
                minSumNotReached.set(false);
            }
            currentShipmentId.set(shipment.getId());
            currentShipmentNumber.set(shipment.getNumber());
            Allure.step("Номер доставки: " + currentShipmentNumber.get());
            log.debug("Номер доставки: {}", currentShipmentNumber.get());
        });
    }

    /**
     * Узнаем вес продукта, полученного через GET v2/departments
     */
    @Step("Получаем вес продукта")
    private double getProductWeight(ProductV2 product) {
        String humanVolume = product.getHumanVolume();

        if (humanVolume.contains(" шт.")) {
            boolean productHasWeightProperty = false;
            Response response = ProductsV2Request.GET(product.getId());
            checkStatusCode200(response);
            List<PropertyV2> properties = response
                    .as(ProductV2Response.class)
                    .getProduct()
                    .getProperties();
            for (PropertyV2 property : properties) {
                if (property.getName().equalsIgnoreCase("вес")) {
                    humanVolume = property.getValue();
                    productHasWeightProperty = true;
                    break;
                }
            }
            if (!productHasWeightProperty) {
                for (PropertyV2 property : properties) {
                    if (property.getName().equalsIgnoreCase("объем")) {
                        humanVolume = property.getValue();
                        break;
                    }
                }
            }
        }
        double productWeight = Double.parseDouble((humanVolume.split(" ")[0]).replace(",", "."));

        if (humanVolume.contains(" кг") || humanVolume.contains(" л")) {
            Allure.step(product + "Вес продукта: " + productWeight + " кг.");
            log.debug("{} Вес продукта: {} кг.", product, productWeight);
            productWeightNotDefined.set(false);
            return productWeight;
        } else if (humanVolume.contains(" г") || humanVolume.contains(" мл")) {
            Allure.step(product + " Вес продукта: " + productWeight / 1000 + "кг.");
            log.debug("{} Вес продукта: {} кг.", product, productWeight / 1000);
            productWeightNotDefined.set(false);
            return productWeight / 1000;
        } else {
            Allure.step(product + " Неизвестный тип веса/объема:" + humanVolume);
            log.debug("{} Неизвестный тип веса/объема: {}", product, humanVolume);
            productWeightNotDefined.set(true);
            return 0;
        }
    }

    @Step("Получаем доступные дни для доставки")
    private List<String> getAvailableDays() {
        Response response = ShipmentsV2Request.ShippingRates.GET(currentShipmentNumber.get(), null);
        checkStatusCode200(response);

        List<String> availableDays = response.as(ShippingRatesV2Response.class).getMeta().getAvailableDays();
        assertFalse(availableDays.isEmpty(),
                "Пустой список available_days в магазине " + currentSid.get() + "\n" + response.body().asString());

        return availableDays;
    }

    @Step("Получаем доступные слоты для доставки")
    private List<ShippingRateV2> getShippingRates(String availableDay) {
        Response response = ShipmentsV2Request.ShippingRates.GET(currentShipmentNumber.get(), availableDay);
        checkStatusCode200(response);

        List<ShippingRateV2> shippingRates = response.as(ShippingRatesV2Response.class).getShippingRates();
        assertFalse(shippingRates.isEmpty(),
                "Нет слотов в магазине " + currentSid.get() + "\n" + response.body().asString());

        return shippingRates;
    }

    /**
     * Получаем первый доступный слот
     */
    @Step("Получаем первый доступный слот")
    public DeliveryWindowV2 getAvailableDeliveryWindow() {
        List<String> availableDays = getAvailableDays();

        List<ShippingRateV2> shippingRates = getShippingRates(availableDays.get(0));

        DeliveryWindowV2 deliveryWindow = shippingRates.get(0).getDeliveryWindow();

        currentDeliveryWindowId.set(deliveryWindow.getId());

        log.debug(deliveryWindow.toString());
        return deliveryWindow;
    }


    /**
     * Получаем первый доступный слот
     */
    @Step("Получаем первый доступный слот ON_DEMAND")
    public DeliveryWindowV2 getAvailableDeliveryWindowOnDemand() {
        List<String> availableDays = getAvailableDays();

        List<ShippingRateV2> shippingRates = getShippingRates(availableDays.get(0));

        List<ShippingRateV2> shippingRatesOnDemand = shippingRates.stream()
                .filter(item -> item.getIsExpressDelivery() == true)
                .collect(Collectors.toList());
        assertFalse(shippingRatesOnDemand.isEmpty(),
                "Нет слотов быстрой доставки в магазине admin/stores/" + currentSid.get());
        DeliveryWindowV2 deliveryWindow = shippingRatesOnDemand.get(shippingRatesOnDemand.size()-1).getDeliveryWindow();

        currentDeliveryWindowId.set(deliveryWindow.getId());

        log.debug(deliveryWindow.toString());
        return deliveryWindow;
    }

    /**
     * Получаем первый доступный слот на завтра
     */
    @Step("Получаем первый доступный слот на завтра")
    public DeliveryWindowV2 getAvailableDeliveryWindowOnTomorrow() {
        Response responseDeliveryWindow = ShipmentsV2Request.ShippingRates.GET(currentShipmentNumber.get(), null);
        checkStatusCode200(responseDeliveryWindow);
        String availableDays = responseDeliveryWindow.as(ShippingRatesV2Response.class).getMeta().getAvailableDays().get(1);

        Response response = ShipmentsV2Request.ShippingRates.GET(currentShipmentNumber.get(), availableDays);
        checkStatusCode200(response);

        List<ShippingRateV2> shippingRates = response.as(ShippingRatesV2Response.class).getShippingRates();

        assertFalse(shippingRates.isEmpty(),
                "Нет слотов в магазине admin/stores/" + currentSid.get());

        DeliveryWindowV2 deliveryWindow = shippingRates.get(0).getDeliveryWindow();

        currentDeliveryWindowId.set(deliveryWindow.getId());

        log.debug(deliveryWindow.toString());
        return deliveryWindow;
    }

    /**
     * Получаем первый доступный способ доставки
     */
    @Step("Получаем первый доступный способ доставки")
    ShippingMethodV2 getAvailableShippingMethod() {
        Response response = ShippingMethodsV2Request.GET(currentSid.get());
        checkStatusCode200(response);
        List<ShippingMethodV2> shippingMethods = response.as(ShippingMethodsV2Response.class).getShippingMethods();

        assertFalse(shippingMethods.isEmpty(), "Нет способов доставки в магазине admin/stores/" + currentSid.get());

        ShippingMethodV2 shippingMethod = shippingMethods.get(0);

        currentShipmentMethodId.set(shippingMethod.getId());

        log.debug(shippingMethod.toString());
        return shippingMethod;
    }

    @Step("Получаем первый доступный способ доставки")
    ShippingMethodV2 getAvailableShippingMethod(final int sid) {
        currentSid.set(sid);
        return getAvailableShippingMethod();
    }

    /**
     * Выбираем id способа оплаты (по умолчанию Картой курьеру)
     */
    @Step("Выбираем id способа оплаты (по умолчанию Картой курьеру)")
    PaymentToolV2 getAvailablePaymentTool() {
        List<PaymentToolV2> paymentTools = getPaymentTools();

        StringJoiner availablePaymentToolsText = new StringJoiner(
                "\n• ",
                "Список доступных способов оплаты:\n• ",
                "\n");
        boolean cardCourier = false;
        PaymentToolV2 selectedPaymentTool = new PaymentToolV2();
        for (int i = 0; i < paymentTools.size(); i++) {
            selectedPaymentTool = paymentTools.get(i);
            String selectedPaymentToolText = selectedPaymentTool + " <<< Выбран";
            if (paymentTools.get(i).getName().equalsIgnoreCase("Картой курьеру")) {
                currentPaymentTool.set(paymentTools.get(i));
                availablePaymentToolsText.add(selectedPaymentToolText);
                cardCourier = true;
            } else if (i == paymentTools.size() - 1 && !cardCourier) { // выбираем последний способ, если нет картой курьеру
                currentPaymentTool.set(paymentTools.get(i));
                availablePaymentToolsText.add(selectedPaymentToolText);
            } else availablePaymentToolsText.add(paymentTools.get(i).toString());
        }
        Allure.step("Доступный список оплаты: " + availablePaymentToolsText);

        log.debug(availablePaymentToolsText.toString());
        return selectedPaymentTool;
    }

    OrderV2 setDefaultOrderAttributes() {
        return setDefaultOrderAttributes("test");
    }

    /**
     * Применяем дефолтные параметры к заказу
     */
    @Step("Применяем параметры к заказу по умолчанию: ")
    OrderV2 setDefaultOrderAttributes(String comment) {
        Response response = OrdersV2Request.PUT(
                //currentAddressId.get(), //параметр ломает оформление заказа в некоторых магазинах
                1,
                userPhone,
                comment,
                currentPaymentTool.get().getId(),
                currentShipmentId.get(),
                currentDeliveryWindowId.get(),
                currentShipmentMethodId.get(),
                currentOrderNumber.get());
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        Allure.step("Применены атрибуты для заказа: " + order.getNumber() + "<br>" +
                "        full_address: " + order.getAddress().getFullAddress() + "<br>" +
                "  replacement_policy: " + order.getReplacementPolicy().getDescription() + "<br>" +
                "  delivery_starts_at: " + order.getShipments().get(0).getDeliveryWindow().getStartsAt() + "<br>" +
                "    delivery_ends_at: " + order.getShipments().get(0).getDeliveryWindow().getEndsAt() + "<br>" +
                "special_instructions: " + order.getSpecialInstructions());

        log.debug("Применены атрибуты для заказа: {}", order.getNumber());
        log.debug("        full_address: {}", order.getAddress().getFullAddress());
        log.debug("  replacement_policy: {}", order.getReplacementPolicy().getDescription());
        log.debug("  delivery_starts_at: {}", order.getShipments().get(0).getDeliveryWindow().getStartsAt());
        log.debug("    delivery_ends_at: {}", order.getShipments().get(0).getDeliveryWindow().getEndsAt());
        log.debug("special_instructions: {}", order.getSpecialInstructions());
        return order;
    }

    /**
     * Применяем дефолтные параметры к заказу
     */
    @Step("Применяем параметры к заказу по умолчанию для on-demand: ")
    OrderV2 setDefaultOrderAttributesOnDemand() {
        Response response = OrdersV2Request.PUT(
                //currentAddressId.get(), //параметр ломает оформление заказа в некоторых магазинах
                1,
                userPhone,
                "test",
                currentPaymentTool.get().getId(),
                currentShipmentId.get(),
                currentDeliveryWindowId.get(),
                "delivery_window_kind",
                currentShipmentMethodId.get(),
                currentOrderNumber.get());
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        Allure.step("Применены атрибуты для заказа: " + order.getNumber() + "<br>" +
                "        full_address: " + order.getAddress().getFullAddress() + "<br>" +
                "  replacement_policy: " + order.getReplacementPolicy().getDescription() + "<br>" +
                "  delivery_starts_at: " + order.getShipments().get(0).getDeliveryWindow().getStartsAt() + "<br>" +
                "    delivery_ends_at: " + order.getShipments().get(0).getDeliveryWindow().getEndsAt() + "<br>" +
                "special_instructions: " + order.getSpecialInstructions());

        log.debug("Применены атрибуты для заказа: {}", order.getNumber());
        log.debug("        full_address: {}", order.getAddress().getFullAddress());
        log.debug("  replacement_policy: {}", order.getReplacementPolicy().getDescription());
        log.debug("  delivery_starts_at: {}", order.getShipments().get(0).getDeliveryWindow().getStartsAt());
        log.debug("    delivery_ends_at: {}", order.getShipments().get(0).getDeliveryWindow().getEndsAt());
        log.debug("special_instructions: {}", order.getSpecialInstructions());
        return order;
    }

    /**
     * Завершаем оформление заказа
     */
    @Step("Завершаем оформление заказа")
    OrderV2 completeOrder() {
        orderCompleted.set(false);
        Response response = OrdersV2Request.Completion.POST(currentOrderNumber.get());
        if (response.getStatusCode() == 422) {
            response = slotAvailabilityCheck(response);
        }
        if (Objects.isNull(response))
            return null;

        checkStatusCode200(response);

        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        List<AlertV2> alerts = order.getShipments().get(0).getAlerts();
        alerts.forEach(alert -> log.error(alert.getFullMessage()));
        Allure.step("Оформлен заказ: " + order.getNumber());
        log.debug("Оформлен заказ: {}", order.getNumber());
        orderCompleted.set(true);
        return order;
    }

    public Response slotAvailabilityCheck(Response response) {
        ErrorsV2 errors = response.as(ErrorResponse.class).getErrors();

        if (errors.getShipments() != null) {
            String notAvailableDeliveryWindow = "Выбранный интервал стал недоступен";
            if (errors.getShipments().contains(notAvailableDeliveryWindow)) {
                Allure.step(notAvailableDeliveryWindow);
                log.error(notAvailableDeliveryWindow);
                getAvailableDeliveryWindow();
                setDefaultOrderAttributes();
                response = OrdersV2Request.Completion.POST(currentOrderNumber.get());
            } else fail(response.body().toString());
        }
        if (errors.getPayments() != null) {
            String notAvailablePaymentMethod = "Заказ не может быть оплачен указанным способом";
            if (errors.getPayments().contains(notAvailablePaymentMethod)) {
                Allure.step(notAvailablePaymentMethod + " " + currentPaymentTool.get());
                fail(notAvailablePaymentMethod + " " + currentPaymentTool.get());
                //ToDo помечать тест желтым, если заказ не может быть оплачен указанным способом
                return null;
            } else fail(response.body().toString());
        }
        return response;
    }

    /**
     * Отменяем заказ по номеру
     */
    @Step("Отменяем заказ по номеру: {orderNumber}")
    public OrderV2 cancelOrder(String orderNumber) {
        Response response = OrdersV2Request.Cancellations.POST(orderNumber, "test");
        checkStatusCode200(response);
        OrderV2 order = response.as(CancellationsV2Response.class).getCancellation().getOrder();
        log.debug("Отменен заказ: {}", order.getNumber());
        return order;
    }

    /**
     * Выбираем первый доступный магазин (берем координаты из объекта необходимого адреса)
     */
    private StoreV2 getCurrentStore(AddressV2 address) {
        Allure.step("Выбираем первый доступный магазин (берем координаты из объекта необходимого адреса)");
        return getCurrentStore(address.getLat(), address.getLon());
    }

    /**
     * Выбираем первый доступный магазин по координатам
     */
    @Step("Выбираем первый доступный магазин по координатам: lat = {lat}, lon = {lon}")
    private StoreV2 getCurrentStore(double lat, double lon) {
        StoreV2 store = getAvailableStores(lat, lon).get(0);

        currentSid.set(store.getId());

        log.debug("Выбран магазин: {}", store);
        return store;
    }

    /**
     * Выбираем первый доступный магазин определенного ритейлера (берем координаты из объекта необходимого адреса)
     */
    @Step("Выбираем первый доступный магазин ритейлера: {retailer} для координат из объекта необходимого адреса: lat = {address.lat}, lon = {address.lon}")
    public StoreV2 getCurrentStore(AddressV2 address, String retailer) {
        if (address.getLat() == null || address.getLon() == null)
            throw new NullPointerException("Не указаны координаты");
        return getCurrentStore(address.getLat(), address.getLon(), retailer);
    }

    /**
     * Выбираем первый доступный магазин определенного ритейлера по координатам
     */
    @Step("Выбираем первый доступный магазин ритейлера {retailer} по координатам lat = {lat}, lon = {lon}")
    private StoreV2 getCurrentStore(double lat, double lon, String retailer) {
        List<StoreV2> stores = getAvailableStores(lat, lon);

        for (StoreV2 store : stores) {
            if (retailer.equalsIgnoreCase(store.getRetailer().getSlug())) {

                currentSid.set(store.getId());

                log.debug("Выбран магазин: {}", store);
                Allure.step("Выбран магазин: " + store);
                return store;
            }
        }
        throw new SkipException("По выбранному адресу нет ретейлера " + retailer);
    }

    /**
     * Получить адрес доставки, зная только sid
     */
    @Step("Получаем адрес доставки по sid = {sid} магазина ")
    public AddressV2 getAddressBySid(int sid) {
        currentSid.set(sid);
        Response response = StoresV2Request.GET(sid);

        if (response.statusCode() == 422) {
            if (response.as(ErrorResponse.class)
                    .getErrors()
                    .getBase()
                    .contains("По указанному адресу"))
                fail("Магазин отключен admin/stores/" + currentSid.get());
        }
        checkStatusCode200(response);
        StoreV2 store = response.as(StoreV2Response.class).getStore();
        if (store == null) fail(response.body().asString());

        AddressV2 address = store.getLocation();

        if (Objects.nonNull(address.getFullAddress())) {
            log.debug("Получен адрес {}", address.getFullAddress());
            Allure.step("Получен адрес " + address.getFullAddress());
        } else log.error("Пустое поле full address");

        List<List<ZoneV2>> zones = store.getZones();
        ZoneV2 zone = getInnerPoint(zones.get(zones.size() - 1));
        address.setCoordinates(zone);
        log.debug("Выбраны координаты: {}", zone);
        Allure.step("Выбраны координаты: " + zone);

        return address;
    }

    /**
     * Получить адрес доставки, зная только sid
     */
    @Step("Получаем адрес доставки по sid = {sid} магазина")
    public AddressV2 getAddressBySidMy(int sid) {
        currentSid.set(sid);
        Response response = StoresV2Request.GET(sid);

        if (response.statusCode() == 422) {
            if (response.as(ErrorResponse.class)
                    .getErrors()
                    .getBase()
                    .contains("По указанному адресу"))
                fail("Магазин отключен admin/stores/" + currentSid.get());
        }
        checkStatusCode200(response);
        StoreV2 store = response.as(StoreV2Response.class).getStore();
        if (store == null) fail(response.body().asString());

        AddressV2 address = store.getLocation();
        log.debug("Получен адрес {}", address.getFullAddress());
        Allure.step("Получен адрес " + address.getFullAddress());

        return address;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    @Step("Получаем список активных ритейлеров: ")
    public List<RetailerV2> getAvailableRetailers() {
        Response response = RetailersV2Request.GET();
        checkStatusCode200(response);
        List<RetailerV2> retailers = response.as(RetailersV2Response.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (RetailerV2 retailer : retailers) {
            availableRetailers.add(retailer.toString());
        }
        log.debug(availableRetailers.toString());
        Allure.addAttachment("Активные ритейлеры:", ContentType.TEXT.toString(), availableRetailers.toString());
        return retailers;
    }

    /**
     * Получить список активных магазинов как список объектов Store (без зон доставки)
     */
    @Step("Получить список активных магазинов как список объектов Store (без зон доставки)")
    public List<StoreV2> getAvailableStores() {
        List<RetailerV2> retailers = getAvailableRetailers();
        List<StoreV2> stores = new ArrayList<>();

        for (RetailerV2 retailer : retailers) {
            Response response = RetailersV1Request.Stores.GET(retailer.getId());
            checkStatusCode200(response);
            List<StoreV2> retailerStores = response.as(StoresV2Response.class).getStores();

            for (StoreV2 retailerStore : retailerStores) {
                retailerStore.setRetailer(retailer);
                stores.add(retailerStore);
            }
        }
        printAvailableStores(stores);

        return stores;
    }

    /**
     * Получить список активных магазинов у ретейлера (без зон доставки)
     */
    @Step("Получить список активных магазинов у ретейлера (без зон доставки)")
    public List<StoreV2> getAvailableStores(RetailerV2 retailer) {
        Response response = RetailersV1Request.Stores.GET(retailer.getId());
        checkStatusCode200(response);

        List<StoreV2> stores = response.as(StoresV2Response.class).getStores();
        for (StoreV2 store : stores) {
            store.setRetailer(retailer);
        }
        printAvailableStores(stores);

        return stores;
    }

    /**
     * Получить по координатам список активных магазинов как список объектов Store
     */
    @Step("Получить по координатам список активных магазинов как список объектов Store")
    private List<StoreV2> getAvailableStores(double lat, double lon) {
        final StoresV2Request.Store store = new StoresV2Request.Store();
        store.setLat(lat);
        store.setLon(lon);
        Response response = StoresV2Request.GET(store);
        checkStatusCode200(response);
        List<StoreV2> stores = response.as(StoresV2Response.class).getStores();

        if (!stores.isEmpty()) {
            printAvailableStores(stores);
        } else {
            Allure.step("По выбранному адресу нет магазинов");
            log.error("По выбранному адресу нет магазинов");
        }

        return stores;
    }


    private void printAvailableStores(List<StoreV2> stores) {
        StringJoiner availableStores = new StringJoiner(
                "\n• ",
                "Список активных магазинов:\n• ",
                "\n");
        for (StoreV2 store : stores) {
            availableStores.add(store.toString());
        }
        Allure.addAttachment("Список активных магазинов:", ContentType.TEXT.toString(), availableStores.toString());
        log.debug(availableStores.toString());
    }

    /**
     * Получить список зон доставки магазина
     */
    @Step("Зоны доставки для магазина с sid = {sid}")
    public List<List<ZoneV2>> storeZones(int sid) {
        Response response = StoresV2Request.GET(sid);
        checkStatusCode200(response);
        return response.as(StoreV2Response.class).getStore().getZones();
    }

    public StoreV2 getStore(int sid) {
        Response response = StoresV2Request.GET(sid);
        checkStatusCode200(response);
        return response.as(StoreV2Response.class).getStore();
    }

    public OrderV2 getCurrentOrder() {
        final Response response = OrdersV2Request.Current.GET();
        checkStatusCode200(response);
        return response.as(OrderV2Response.class).getOrder();
    }

    public OrderV2 getOrder(String orderNumber) {
        final Response response = OrdersV2Request.GET(orderNumber);
        checkStatusCode200(response);
        return response.as(OrderV2Response.class).getOrder();
    }

    /**
     * Узнаем номер заказа
     */
    @Step("Узнаем номер заказа")
    public String getCurrentOrderNumber() {
        Response response = OrdersV2Request.POST();
        checkStatusCode200(response);
        currentOrderNumber.set(response
                .as(OrderV2Response.class)
                .getOrder()
                .getNumber());
        Allure.step("Номер текущего заказа: " + currentOrderNumber.get());
        log.debug("Номер текущего заказа: {}", currentOrderNumber.get());
        return currentOrderNumber.get();
    }

    /**
     * Узнаем shipment number заказа
     */
    public String getShipmentsNumber() {
        Response response = OrdersV2Request.POST();
        checkStatusCode200(response);
        currentShipmentNumber.set(response
                .as(OrderV2Response.class)
                .getOrder()
                .getShipments().get(0)
                .getNumber());
        Allure.step("SHIPMENT_NUMBER текущего заказа: " + currentShipmentNumber.get());
        log.debug("SHIPMENT_NUMBER текущего заказа: {}", currentShipmentNumber.get());
        return currentShipmentNumber.get();
    }

    public OrderV2 getOpenOrder() {
        Response response = OrdersV2Request.POST();
        checkStatusCode200(response);
        return response.as(OrderV2Response.class).getOrder();
    }

    @Step("Получаем список способов оплаты")
    public List<PaymentToolV2> getPaymentTools() {
        Response response = PaymentToolsV2Request.GET();
        checkStatusCode200(response);
        return response.as(PaymentToolsV2Response.class).getPaymentTools();
    }

    @Step("Добавляем кредитную карту {0}")
    public void addCreditCard(final CreditCardsV2Request.CreditCard creditCard) {
        final var response = CreditCardsV2Request.POST(creditCard);
        checkStatusCode200(response);
    }

    @Step("Получение списка способов доставки для sid = {sid}")
    public List<ShippingMethodV2> getShippingMethods(int sid) {
        Response response = ShippingMethodsV2Request.GET(sid);
        checkStatusCode200(response);
        return response.as(ShippingMethodsV2Response.class).getShippingMethods();
    }

    public List<ShippingMethodV2> getShippingMethods() {
        return getShippingMethods(currentSid.get());
    }

    @Step("Получаем данные доставки для заказа")
    public ShipmentV2 getShippingWithOrder() {
        final Response response = OrdersV2Request.Current.GET();
        checkStatusCode200(response);
        return response
                .as(OrderV2Response.class)
                .getOrder()
                .getShipments()
                .get(0);
    }


    /**
     * Получаем список активных (принят, собирается, в пути) заказов
     */
    @Step("Список активных (принят, собирается, в пути) заказов: ")
    private List<OrderV2> getActiveOrders() {
        OrdersV2Response response = OrdersV2Request.GET(OrderStatusV2.ACTIVE).as(OrdersV2Response.class);
        List<OrderV2> orders = response.getOrders();

        if (orders.isEmpty()) {
            Allure.step("Нет активных заказов");
            log.warn("Нет активных заказов");
        } else {
            int pages = response.getMeta().getTotalPages();
            if (pages > 1) {
                for (int i = 2; i <= pages; i++) {
                    orders.addAll(OrdersV2Request.GET(OrderStatusV2.ACTIVE, i).as(OrdersV2Response.class).getOrders());
                }
            }
            log.debug("Список активных заказов:");
            orders.forEach(order -> log.debug(order.getNumber()));
        }
        return orders;
    }

    public void fillCart(List<ProductV2> products) {
        fillCart(products, 1);
    }

    public void fillCart(List<ProductV2> products, int limit) {
        products = products.stream().limit(limit).collect(Collectors.toList());
        double initialCartWeight = 0;

        for (final ProductV2 product : products) {
            addItemToCart(product.getId(), 1);
            initialCartWeight += (getProductWeight(product) * product.getItemsPerPack());
        }

        getMinSumFromAlert();

        int quantity = 1;
        for (final ProductV2 product : products) {
            if (minSumNotReached.get()) {
                quantity = (int) Math.ceil(minSum.get() / (product.getPrice() * product.getItemsPerPack()));
            }

            var finalCartWeight = roundBigDecimal((getProductWeight(product) * quantity) + initialCartWeight, 3);

            var cartWeightText = "Вес корзины: " + finalCartWeight + " кг.\n";
            var anotherProductText = "Выбираем другой товар\n";

            if (finalCartWeight > 40) log.error(cartWeightText + anotherProductText);
            else if (productWeightNotDefined.get()) log.error(anotherProductText);
            else {
                log.debug(cartWeightText);
                if (minSumNotReached.get()) addItemToCart(product.getId(), quantity);
                break;
            }
        }
    }

    public FavoritesItemV2Response addFavoritesProductBySid(Integer sid) {
        ProductV2 product = getProductFromEachDepartmentInStore(sid).get(0);
        final Response response = FavoritesV2Request.POST(product.getId());
        checkStatusCode200(response);
        return response.as(FavoritesItemV2Response.class);
    }

    public List<ProductV2> addFavoritesQtyListProductBySid(Integer sid, Integer qty) {
        List<ProductV2> products = getProductFromEachDepartmentInStore(sid);
        List<ProductV2> productsList = new ArrayList<>();
        products.stream()
                .limit(qty + 1)
                .forEach(item -> {
                            FavoritesV2Request.POST(item.getId()).then()
                                    .statusCode(anyOf(is(200), is(422)));
                            productsList.add(item);
                        }
                );
        return productsList;
    }

    public void addFavoritesListProductBySid(final int sid, final int count) {
        getProductsFromEachDepartmentInStore(sid)
                .stream()
                .limit(count)
                .forEach(product -> {
                    var response = FavoritesV2Request.POST(product.getId());
                    log.debug("Product '{}' was added with status = {}", product.getName(), response.statusCode());
                });
    }

    public void addFavoriteByProductId(final int id) {
        var response = FavoritesV2Request.POST(id);
        log.debug("Product '{}' was added with status = {}", id, response.statusCode());
    }

    public OrderV2 setDefaultAttributesAndCompleteOrder() {
        return setDefaultAttributesAndCompleteOrder("test");
    }

    /**
     * Применяем атрибуты заказа (способ оплаты и слот) и завершаем его
     */
    public OrderV2 setDefaultAttributesAndCompleteOrder(String comment) {
        getAvailablePaymentTool();
        getAvailableShippingMethod();
        getAvailableDeliveryWindow();

        setDefaultOrderAttributes(comment);
        return completeOrder();
    }

    /**
     * Поменять адрес у юзера
     */
    public void setAddress(UserData user, AddressV2 address) {
        getCurrentOrderNumber();

        setAddressAttributes(address);
    }

    /**
     * Наполнить корзину и выбрать адрес по умолчанию у юзера по определенному адресу
     */
    public void dropAndFillCart(UserData user, AddressV2 address) {
        dropCart(user, address);

        getCurrentStore(address);
        fillCart(getProducts(currentSid.get()));
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера по определенному адресу у определенного ритейлера
     */
    public void dropAndFillCart(UserData user, AddressV2 address, String retailer) {
        dropCart(user, address);

        getCurrentStore(address, retailer);
        fillCart(getProducts(currentSid.get()));
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера в определенном магазине
     */
    @Step("Наполнение корзины для пользователя {user.email} в магазине с sid={sid}")
    public List<LineItemV2> dropAndFillCart(UserData user, int sid) {
        dropCart(user, getAddressBySid(sid));
        fillCart(getProducts(sid));
        return getLineItems(getCurrentOrderNumber());
    }

    /**
     * Наполнить корзину товарами с определенным типом цены и выбрать адрес у юзера в определенном магазине
     */
    @Step("Наполнение корзины для пользователя {user.email} в магазине с sid={sid} товарами с типом цены {priceType}")
    public List<LineItemV2> dropAndFillCart(UserData user, int sid, ProductPriceTypeV2 priceType) {
        dropCart(user, getAddressBySid(sid));
        fillCart(getProducts(sid, priceType));
        return getLineItems(getCurrentOrderNumber());
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера в определенном магазине по определенным координатам
     */
    @Step("Наполнение корзины для пользователя {user.email} в магазине с sid={sid} по определенным координатам {coordinates.lat}/{coordinates.lon} ")
    public void dropAndFillCart(UserData user, int sid, ZoneV2 coordinates) {
        AddressV2 address = getAddressBySid(sid);
        address.setCoordinates(coordinates);

        dropCart(user, address);

        fillCart(getProducts(sid));
    }

    /**
     * Очистить корзину и выбрать адрес у юзера
     */
    @Step("Очистить корзину пользователя {user.email}")
    public void dropCart(UserData user, AddressV2 address) {
        getCurrentOrderNumber();
        deleteAllShipments();

        setAddressAttributes(user, address);
    }


    @Step("Заполнение корзины и аттрибутов заказа без оформления")
    public void fillingCartAndOrderAttributesWithoutCompletion(UserData user, int sid) {
        dropAndFillCart(user, sid);
        getAvailablePaymentTool();
        getAvailableShippingMethod();
        getAvailableDeliveryWindow();
        setDefaultOrderAttributes();
    }

    @Step("Заполнение корзины и аттрибутов заказа без оформления")
    public void fillingCartAndOrderAttributesWithoutCompletion(UserData user, AddressV2 address) {
        dropAndFillCart(user, address);
        getAvailablePaymentTool();
        getAvailableShippingMethod();
        getAvailableDeliveryWindow();
        setDefaultOrderAttributes();
    }


    /**
     * Оформить тестовый заказ у юзера по определенному адресу
     */
    @Step("Оформляем заказ у юзера {user.email} по адресу {address.fullAddress} для ритейлера {retailer}")
    public OrderV2 order(UserData user, AddressV2 address, String retailer) {
        dropAndFillCart(user, address, retailer);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    @Step("Оформляем заказ у юзера {user.email} в магазине с sid = {sid}")
    public OrderV2 order(UserData user, int sid) {
        dropAndFillCart(user, sid);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине с выбранным типом оплаты
     */
    @Step("Оформляем заказ у юзера {user.email} в магазине с sid = {sid} с типом оплаты {priceType}")
    public OrderV2 order(UserData user, int sid, ProductPriceTypeV2 priceType) {
        dropCart(user, getAddressBySid(sid));
        fillCart(getProducts(sid, priceType));
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    @Step("Оформляем заказ у юзера {user.email} в магазине с sid = {sid} c количеством товаров в корзине = {itemsNumber}")
    public OrderV2 order(UserData user, int sid, int itemsNumber, String comment) {
        dropCart(user, getAddressBySid(sid));
        fillCart(getProducts(sid), itemsNumber);
        return setDefaultAttributesAndCompleteOrder(comment);
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине по определенным координатам
     */
    @Step("Оформляем заказ у юзера {user.email} в магазине с sid = {sid} c координатами lat/lon = {coordinates.lat}/{coordinates.lon}")
    public OrderV2 order(UserData user, int sid, ZoneV2 coordinates) {
        dropAndFillCart(user, sid, coordinates);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Отменить последний заказ (с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelCurrentOrder() {
        if (currentOrderNumber.get() != null && orderCompleted.get() != null && orderCompleted.get())
            cancelOrder(currentOrderNumber.get());
    }

    /**
     * Отменить активные (принят, собирается, в пути) заказы
     */
    @Step("Отменяем активные (принят, собирается, в пути) заказы")
    public void cancelActiveOrders() {
        getActiveOrders().forEach(order -> cancelOrder(order.getNumber()));
    }

    @Step("Получаем дынные о номерах телефонов")
    public PhonesV2Response getPhoneId() {
        Response response = PhonesV2Request.GET();
        checkStatusCode200(response);
        return response.as(PhonesV2Response.class);
    }

    public String getSimpleAdsFirstImage(final SimpleAdsV2Request.SimpleAdsV2 allRequiredParameters) {

        final Response response = SimpleAdsV2Request.POST(allRequiredParameters);
        checkStatusCode200(response);
        final SimpleAdsV2Response simpleAdsV2Response = response.as(SimpleAdsV2Response.class);
        if (simpleAdsV2Response.getMedia() == null || simpleAdsV2Response.getMedia().isEmpty()) {
            throw new SkipException("Рекомендаций нет");
        }

        return simpleAdsV2Response.getMedia().stream()
                .iterator().next()
                .getAssets().stream()
                .filter(img -> Objects.nonNull(img.getImage()))
                .iterator().next()
                .getImage().getUrl();
    }

    public void waitingForDeliveryStatus(final String orderNumber) {
        String shipmentState;
        int i = 0;
        do {
            Response response = OrdersV2Request.GET(orderNumber);
            checkStatusCode200(response);
            OrderV2Response order = response.as(OrderV2Response.class);
            shipmentState = order.getOrder().getShipmentState();
            log.info("shipment state: {}", shipmentState);
            simplyAwait(1);
            i += 1;
        } while (!shipmentState.equals("shipped") && i < 10);
    }

    public List<ReviewIssueV2> getReviewIssues(final String shipmentsNumber) {
        final Response response = ShipmentsV2Request.ReviewIssues.GET(shipmentsNumber);
        checkStatusCode200(response);
        ReviewIssuesV2Response reviewIssuesV2Response = response.as(ReviewIssuesV2Response.class);
        return reviewIssuesV2Response.getReviewIssues();
    }

    public ProfileV2Response getProfile() {
        final Response response = ProfileV2Request.GET();
        checkStatusCode200(response);
        return response.as(ProfileV2Response.class);
    }

    @Step("Получаем координаты из зоны доставки магазина")
    public ZoneV2 getCoordinates(final Integer storeId) {
        List<List<ZoneV2>> zones = storeZones(storeId);
        return getInnerPoint(zones.get(0));
    }

    @Step("Получение реферального промокода для пользователя с ID {userId}")
    public String getReferralPromotionCode(final String userId) {
        final Response response = UsersV2Request.ReferralProgram.GET(userId);
        checkStatusCode200(response);
        return response.as(UserReferralProgramV2Response.class).getUserReferralProgram().getCode();
    }

    /**
     * @param user должен иметь phone и encryptedPhone
     *             encryptedPhone получается с помощью рельсовой команды Ciphers::AES.encrypt(‘’, key: ENV[‘CIPHER_KEY_PHONE’])
     */
    @Step("Регистрация/авторизация по номеру телефона с помощью API")
    public void authByPhone(final UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V2, SessionProvider.PHONE, user);
    }

    public void authByQA(final UserData user) {
        SessionFactory.createSessionToken(SessionType.API_V2, SessionProvider.QA, user);
    }

    @Step("Меняем адрес пользователя и добавляем товар в корзину")
    public LineItemV2 changeAddressAndAddItemToCart(AddressV2 address, Long offerId, Integer quantity) {
        setAddressAttributes(address);
        return addItemToCart(offerId, quantity);
    }
}
