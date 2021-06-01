package ru.instamart.api.helper;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.OrderStatusV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.model.v2.*;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.request.v2.*;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v1.OffersV1Response;
import ru.instamart.api.response.v2.*;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.lib.Pages;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.kraken.util.MapUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Slf4j
public final class InstamartApiHelper {

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
            lat = roundBigDecimal(lat + stepLat,7);
            lon = roundBigDecimal(lon + stepLon,7);

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
                MapUtil.hasPairInMap(store.getId(), zoneName, pickupZones)) {
            throw new SkipException("Доступен только самовывоз\nsid: " + store.getId() + "\n" + zoneName);
        }
    }

    public void skipTestIfOnlyPickupIsAvailable(StoreV2 store) {
        skipTestIfOnlyPickupIsAvailable(store, null);
    }

    /*
      МЕТОДЫ ОБРАБОТКИ ОТВЕТОВ API V2
     */

    public List<TaxonV2> getTaxons(int sid) {
        return TaxonsV2Request.GET(sid).as(TaxonsV2Response.class).getTaxons();
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
    private void deleteItemsFromCart() {
        Response response = OrdersV2Request.Shipments.DELETE(currentOrderNumber.get());

        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        log.info("Удалены все доставки у заказа: {}", order.getNumber());
    }

    /**
     * Изменение/применение параметров адреса из объекта адреса с указанием имени и фамилии юзера
     */
    private void setAddressAttributes(UserData user, AddressV2 address) {
        address.setFirstName(user.getFirstName());
        address.setLastName(user.getLastName());

        setAddressAttributes(address);
    }

    /**
     * Изменение/применение параматров адреса из объекта адреса
     */
    private void setAddressAttributes(AddressV2 address) {
        Response response = OrdersV2Request.ShipAddressChange.PUT(address, currentOrderNumber.get());

        AddressV2 addressFromResponse = response
                .as(ShipAddressChangeV2Response.class)
                .getShipAddressChange()
                .getOrder()
                .getAddress();
        currentAddressId.set(addressFromResponse.getId());

        log.info("Применен адрес: {}", addressFromResponse);
    }

    /** Получаем список продуктов: по одному из каждой категории */
    public List<ProductV2> getProductFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 1, new SoftAssert());
    }

    /** Получаем список продуктов: максимум (6) из каждой категории */
    public List<ProductV2> getProductsFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 6, new SoftAssert());
    }

    /** Получаем список продуктов: максимум (6) из каждой категории и проверяем корректность категорий */
    public List<ProductV2> getProductsFromEachDepartmentInStore(int sid, SoftAssert softAssert) {
        return getProductsFromEachDepartmentInStore(sid, 6, softAssert);
    }

    /**
     * Получаем список продуктов
     * @param sid сид магазина
     * @param numberOfProductsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    private List<ProductV2> getProductsFromEachDepartmentInStore(
            int sid, int numberOfProductsFromEachDepartment, SoftAssert softAssert) {
        List<DepartmentV2> departments = DepartmentsV2Request.GET(sid, numberOfProductsFromEachDepartment)
                .as(DepartmentsV2Response.class).getDepartments();

        String storeUrl = EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + "?sid=" + sid;

        Assert.assertNotEquals(
                departments.size(),
                0,
                "Не импортированы товары для магазина\n" + storeUrl);

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

    /**
     * Добавляем список товаров в корзину
     */
    public void addItemsToCart(List<ProductV2> products, int quantity) {
        for (ProductV2 product : products) {
            addItemToCart(product.getId(), quantity);
        }
    }

    /**
     * Добавляем товар в корзину
     */
    private void addItemToCart(long productId, int quantity) {
        Response response = LineItemsV2Request.POST(productId, quantity, currentOrderNumber.get());
        checkStatusCode200(response);
        LineItemV2 lineItem = response.as(LineItemV2Response.class).getLineItem();

        log.info(lineItem.toString());
    }

    public int getMinOrderAmount(int sid) {
        double minSum = getStore(sid).getMinOrderAmount();
        log.info("Минимальная сумма корзины: {}", minSum);
        this.minSum.set((int) minSum);
        return (int) minSum;
    }

    public int getMinFirstOrderAmount(int sid) {
        double minSum = getStore(sid).getMinFirstOrderAmount();
        log.info("Минимальная сумма корзины: {}", minSum);
        this.minSum.set((int) minSum);
        return (int) minSum;
    }

    /**
     * Получаем id и номер шипмента
     * Получаем минимальную сумму заказа, если сумма не набрана
     */
    private void getMinSumFromAlert() {
        final Response response = OrdersV2Request.Current.GET();
        checkStatusCode200(response);
        final ShipmentV2 shipment = response
                .as(OrderV2Response.class)
                .getOrder()
                .getShipments()
                .get(0);
        if (shipment.getAlerts().size() > 0) {
            String alertMessage = shipment.getAlerts().get(0).getMessage().replaceAll("[^0-9]","");
            minSum.set(Integer.parseInt(alertMessage.substring(0, alertMessage.length() - 2)));
            log.info("Минимальная сумма корзины: {}", minSum.get());
            minSumNotReached.set(true);
        } else {
            log.warn("Минимальная сумма корзины набрана");
            minSumNotReached.set(false);
        }
        currentShipmentId.set(shipment.getId());
        currentShipmentNumber.set(shipment.getNumber());
        log.info("Номер доставки: {}", currentShipmentNumber.get());
    }

    /**
     * Узнаем вес продукта, полученного через GET v2/departments
     */
    private double getProductWeight(ProductV2 product) {
        String humanVolume = product.getHumanVolume();

        if (humanVolume.contains(" шт.")) {
            boolean productHasWeightProperty = false;
            List<PropertyV2> properties = ProductsV2Request.GET(product.getId())
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
        double productWeight = Double.parseDouble((humanVolume.split(" ")[0]).replace(",","."));

        if (humanVolume.contains(" кг") || humanVolume.contains(" л")) {
            log.info("{} Вес продукта: {} кг.",product, productWeight);
            productWeightNotDefined.set(false);
            return productWeight;
        } else if (humanVolume.contains(" г") || humanVolume.contains(" мл")) {
            log.info("{} Вес продукта: {} кг.", product, productWeight / 1000);
            productWeightNotDefined.set(false);
            return productWeight / 1000;
        } else {
            log.info("{} Неизвестный тип веса/объема: {}", product, humanVolume);
            productWeightNotDefined.set(true);
            return 0;
        }
    }

    /**
     * Получаем первый доступный слот
     */
    private void getAvailableDeliveryWindow() {
        List<ShippingRateV2> shippingRates = ShipmentsV2Request.ShippingRates
                .GET(currentShipmentNumber
                        .get()).as(ShippingRatesV2Response.class).getShippingRates();

        Assert.assertNotEquals(
                shippingRates.size(),
                0,
                "Нет слотов в магазине " + Pages.Admin.stores(currentSid.get()));

        DeliveryWindowV2 deliveryWindow = shippingRates.get(0).getDeliveryWindow();

        currentDeliveryWindowId.set(deliveryWindow.getId());

        log.info(deliveryWindow.toString());
    }

    /**
     * Получаем первый доступный способ доставки
     */
    private void getAvailableShippingMethod() {
        List<ShippingMethodV2> shippingMethods = ShippingMethodsV2Request.GET(currentSid.get())
                .as(ShippingMethodsV2Response.class).getShippingMethods();

        Assert.assertNotEquals(
                shippingMethods.size(),
                0,
                "Нет способов доставки в магазине\n" + Pages.Admin.stores(currentSid.get()));

        ShippingMethodV2 shippingMethod = shippingMethods.get(0);

        currentShipmentMethodId.set(shippingMethod.getId());

        log.info(shippingMethod.toString());
    }

    /**
     * Выбираем id способа оплаты (по умолчанию Картой курьеру)
     */
    private void getAvailablePaymentTool() {
        List<PaymentToolV2> paymentTools = PaymentToolsV2Request.GET().as(PaymentToolsV2Response.class).getPaymentTools();

        StringJoiner availablePaymentTools = new StringJoiner(
                "\n• ",
                "Список доступных способов оплаты:\n• ",
                "\n");
        boolean cardCourier = false;
        for (int i = 0; i < paymentTools.size(); i++) {
            String selectedPaymentTool = paymentTools.get(i) + " <<< Выбран";
            if (paymentTools.get(i).getName().equalsIgnoreCase("Картой курьеру")) {
                currentPaymentTool.set(paymentTools.get(i));
                availablePaymentTools.add(selectedPaymentTool);
                cardCourier = true;
            } else if (i == paymentTools.size() - 1 && !cardCourier) { // выбираем последний способ, если нет картой курьеру
                currentPaymentTool.set(paymentTools.get(i));
                availablePaymentTools.add(selectedPaymentTool);
            } else availablePaymentTools.add(paymentTools.get(i).toString());
        }
        log.info(availablePaymentTools.toString());
    }

    /**
     * Применяем дефолтные параметры к заказу
     */
    private void setDefaultOrderAttributes() {
        Response response = OrdersV2Request.PUT(
                //currentAddressId.get(), //параметр ломает оформление заказа в некоторых магазинах
                1,
                "+7 (987) 654 32 10",
                "test",
                currentPaymentTool.get().getId(),
                currentShipmentId.get(),
                currentDeliveryWindowId.get(),
                currentShipmentMethodId.get(),
                currentOrderNumber.get());
        checkStatusCode200(response);
        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        log.info("Применены атрибуты для заказа: {}", order.getNumber());
        log.info("        full_address: {}", order.getAddress().getFullAddress());
        log.info("  replacement_policy: {}", order.getReplacementPolicy().getDescription());
        log.info("  delivery_starts_at: {}", order.getShipments().get(0).getDeliveryWindow().getStartsAt());
        log.info("    delivery_ends_at: {}", order.getShipments().get(0).getDeliveryWindow().getEndsAt());
        log.info("special_instructions: {}", order.getSpecialInstructions());
    }

    /**
     * Завершаем оформление заказа
     */
    private OrderV2 completeOrder() {
        orderCompleted.set(false);
        Response response = OrdersV2Request.Completion.POST(currentOrderNumber.get());
        if (response.getStatusCode() == 422) {
            ErrorsV2 errors = response.as(ErrorResponse.class).getErrors();

            if (errors.getShipments() != null) {
                String notAvailableDeliveryWindow = "Выбранный интервал стал недоступен";
                if (errors.getShipments().contains(notAvailableDeliveryWindow)) {
                    log.error(notAvailableDeliveryWindow);
                    getAvailableDeliveryWindow();
                    setDefaultOrderAttributes();
                    response = OrdersV2Request.Completion.POST(currentOrderNumber.get());
                } else Assert.fail(response.body().toString());
            }
            if (errors.getPayments() != null) {
                String notAvailablePaymentMethod = "Заказ не может быть оплачен указанным способом";
                if (errors.getPayments().contains(notAvailablePaymentMethod)) {
                    log.error("{} {}", notAvailablePaymentMethod, currentPaymentTool.get());
                    //ToDo помечать тест желтым, если заказ не может быть оплачен указанным способом
                    return null;
                } else Assert.fail(response.body().toString());
            }
        }
        checkStatusCode200(response);

        OrderV2 order = response.as(OrderV2Response.class).getOrder();
        List<AlertV2> alerts = order.getShipments().get(0).getAlerts();

        for (AlertV2 alert : alerts)
            log.error(alert.getFullMessage());

        log.info("Оформлен заказ: {}", order.getNumber());
        orderCompleted.set(true);
        return order;
    }

    /**
     * Отменяем заказ по номеру
     */
    private void cancelOrder(String orderNumber) {
        Response response = OrdersV2Request.Cancellations.POST(orderNumber, "test");
        checkStatusCode200(response);
        OrderV2 order = response.as(CancellationsV2Response.class).getCancellation().getOrder();
        log.info("Отменен заказ: {}", order.getNumber());
    }

    /**
     * Выбираем первый доступный магазин (берем координаты из обьекта необходимого адреса)
     */
    private void getCurrentSid(AddressV2 address) {
        getCurrentSid(address.getLat(), address.getLon());
    }

    /**
     * Выбираем первый доступный магазин по координатам
     */
    private void getCurrentSid(double lat, double lon) {
        StoreV2 store = availableStores(lat, lon).get(0);

        currentSid.set(store.getId());

        log.info("Выбран магазин: {}", store);
    }

    /**
     * Выбираем первый доступный магазин определенного ритейлера (берем координаты из обьекта необходимого адреса)
     */
    private void getCurrentSid(AddressV2 address, String retailer) {
        if (address.getLat() == null || address.getLon() == null)
            throw new NullPointerException("Не указаны координаты");
        getCurrentSid(address.getLat(), address.getLon(), retailer);
    }

    /**
     * Выбираем первый доступный магазин определенного ритейлера по координатам
     */
    private void getCurrentSid(double lat, double lon, String retailer) {
        List<StoreV2> stores = availableStores(lat, lon);

        for (StoreV2 store : stores) {
            if (retailer.equalsIgnoreCase(store.getRetailer().getSlug())) {

                currentSid.set(store.getId());

                log.info("Выбран магазин: {}", store);
                return;
            }
        }
        log.error("По выбранному адресу нет ретейлера {}", retailer);
    }

    /**
     * Получить адрес доставки, зная только sid
     */
    private AddressV2 getAddressBySid(int sid) {
        currentSid.set(sid);
        Response response = StoresV2Request.GET(sid);

        if (response.statusCode() == 422)
            if (response.as(ErrorResponse.class)
                    .getErrors()
                    .getBase()
                    .contains("По указанному адресу"))
                Assert.fail("Магазин отключен " + Pages.Admin.stores(currentSid.get()));

        StoreV2 store = response.as(StoreV2Response.class).getStore();

        AddressV2 address = store.getLocation();
        log.info("Получен адрес {}", address.getFullAddress());

        List<List<ZoneV2>> zones = store.getZones();
        ZoneV2 zone = getInnerPoint(zones.get(zones.size() - 1));
        address.setCoordinates(zone);
        log.info("Выбраны координаты: {}", zone);

        return address;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public List<RetailerV2> availableRetailers() {
        List<RetailerV2> retailers = RetailersV2Request.GET().as(RetailersV2Response.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (RetailerV2 retailer : retailers) {
            availableRetailers.add(retailer.toString());
        }
        log.info(availableRetailers.toString());

        return retailers;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public List<RetailerV2> availableRetailersSpree() {
        List<RetailerV2> retailers = RetailersV1Request.GET().as(RetailersV2Response.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (RetailerV2 retailer : retailers)
            if (retailer.getAvailable()) availableRetailers.add(retailer.toString());
        log.info(availableRetailers.toString());

        StringJoiner notAvailableRetailers = new StringJoiner(
                "\n• ",
                "Список неактивных ретейлеров:\n• ",
                "\n");
        for (RetailerV2 retailer : retailers)
            if (!retailer.getAvailable()) notAvailableRetailers.add(retailer.toString());
        log.info(notAvailableRetailers.toString());

        return retailers;
    }

    /**
     * Получить список активных магазинов как список объектов Store (без зон доставки)
     */
    public List<StoreV2> availableStores() {
        List<RetailerV2> retailers = availableRetailers();
        List<StoreV2> stores = new ArrayList<>();

        for (RetailerV2 retailer : retailers) {
            List<StoreV2> retailerStores = RetailersV1Request.Stores.GET(retailer.getId()).as(StoresV2Response.class).getStores();
            for (StoreV2 retailerStore: retailerStores) {
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
    public List<StoreV2> availableStores(RetailerV2 retailer) {
        List<StoreV2> stores = RetailersV1Request.Stores.GET(retailer.getId()).as(StoresV2Response.class).getStores();
        for (StoreV2 store : stores) {
            store.setRetailer(retailer);
        }
        printAvailableStores(stores);

        return stores;
    }

    /**
     * Получить по координатам список активных магазинов как список объектов Store
     */
    private List<StoreV2> availableStores(double lat, double lon) {
        final StoresV2Request.Store store = new StoresV2Request.Store();
        store.setLat(lat);
        store.setLon(lon);
        List<StoreV2> stores = StoresV2Request.GET(store).as(StoresV2Response.class).getStores();

        if (stores.size() > 0) {
            printAvailableStores(stores);
        } else {
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
        log.info(availableStores.toString());
    }

    /**
     * Получить список зон доставки магазина
     */
    public List<List<ZoneV2>> storeZones(int sid) {
        return StoresV2Request.GET(sid).as(StoreV2Response.class).getStore().getZones();
    }

    public StoreV2 getStore(int sid) {
        return StoresV2Request.GET(sid).as(StoreV2Response.class).getStore();
    }

    /**
     * Узнаем номер заказа
     */
    public String getCurrentOrderNumber() {
        Response response = OrdersV2Request.POST();
        checkStatusCode200(response);
        currentOrderNumber.set(response
                .as(OrderV2Response.class)
                .getOrder()
                .getNumber());
        log.info("Номер текущего заказа: {}", currentOrderNumber.get());
        return currentOrderNumber.get();
    }

    /**
     * Получаем список активных (принят, собирается, в пути) заказов
     */
    private List<OrderV2> activeOrders() {
        OrdersV2Response response = OrdersV2Request.GET(OrderStatusV2.ACTIVE).as(OrdersV2Response.class);
        List<OrderV2> orders = response.getOrders();

        if (orders.size() < 1) {
            log.warn("Нет активных заказов");
        } else {
            int pages = response.getMeta().getTotalPages();
            if (pages > 1) {
                for (int i = 2; i <= pages; i++) {
                    orders.addAll(OrdersV2Request.GET(OrderStatusV2.ACTIVE, i).as(OrdersV2Response.class).getOrders());
                }
            }
            log.info("Список активных заказов:");
            for (OrderV2 order : orders) {
                log.info(order.getNumber());
            }
        }
        return orders;
    }

    public List<OfferV1> getActiveOffers(String storeUuid) {
        return StoresV1Request.Offers.GET(
                storeUuid,
                "вода",
                "")
                .as(OffersV1Response.class)
                .getOffers()
                .stream()
                .filter(OfferV1::getActive)
                .collect(Collectors.toList());
    }

    /*
      МЕТОДЫ ИЗ НЕСКОЛЬКИХ ЗАПРОСОВ
     */

    /**
     * Наполняем корзину разными товарами до минимальной суммы заказа в конкретном магазине
     */
    private void fillCartOnSid(int sid, int items) {
        List<ProductV2> products = getProductFromEachDepartmentInStore(sid);
        double initialCartWeight = 0;
        for (int i = 0; i < items; i++) {
            ProductV2 product = products.get(i);
            addItemToCart(product.getId(),1);
            initialCartWeight += (getProductWeight(product) * product.getItemsPerPack());
        }
        getMinSumFromAlert();
        int quantity = 1;
        for (ProductV2 product : products) {
            if (minSumNotReached.get())
                quantity = (int) Math.ceil(minSum.get() / (product.getPrice() * product.getItemsPerPack()));

            double finalCartWeight = roundBigDecimal(
                    (getProductWeight(product) * quantity) + initialCartWeight, 3);

            String cartWeightText = "Вес корзины: " + finalCartWeight + " кг.\n";
            String anotherProductText = "Выбираем другой товар\n";
            if (finalCartWeight > 40) log.error(cartWeightText + anotherProductText);
            else if (productWeightNotDefined.get()) log.error(anotherProductText);
            else {
                log.info(cartWeightText);
                if (minSumNotReached.get()) addItemToCart(product.getId(), quantity);
                break;
            }
        }
    }

    /**
     * Наполняем корзину до минимальной суммы заказа в конкретном магазине
     */
    private void fillCartOnSid(int sid) {
        fillCartOnSid(sid,1);
    }

    /**
     * Применяем атрибуты заказа (способ оплаты и слот) и завершаем его
     */
    private OrderV2 setDefaultAttributesAndCompleteOrder() {
        getAvailablePaymentTool();
        getAvailableShippingMethod();
        getAvailableDeliveryWindow();

        setDefaultOrderAttributes();
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
    public void fillCart(UserData user, AddressV2 address) {
        dropCart(user, address);

        getCurrentSid(address);
        fillCartOnSid(currentSid.get());
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера по определенному адресу у определенного ритейлера
     */
    public void fillCart(UserData user, AddressV2 address, String retailer) {
        dropCart(user, address);

        getCurrentSid(address, retailer);
        fillCartOnSid(currentSid.get());
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера в определенном магазине
     */
    public void fillCart(UserData user, int sid) {
        dropCart(user, getAddressBySid(sid));

        fillCartOnSid(sid);
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера в определенном магазине по определенным координатам
     */
    public void fillCart(UserData user, int sid, ZoneV2 coordinates) {
        AddressV2 address = getAddressBySid(sid);
        address.setCoordinates(coordinates);

        dropCart(user, address);

        fillCartOnSid(sid);
    }

    /**
     * Очистить корзину и выбрать адрес у юзера
     */
    public void dropCart(UserData user, AddressV2 address) {
        SessionFactory.createSessionToken(SessionType.API_V2, user);
        getCurrentOrderNumber();
        deleteItemsFromCart();

        setAddressAttributes(user, address);
    }

    /**
     * Оформить тестовый заказ у юзера по определенному адресу
     */
    public OrderV2 order(UserData user, AddressV2 address, String retailer) {
        fillCart(user, address, retailer);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    public OrderV2 order(UserData user, int sid) {
        fillCart(user, sid);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    public OrderV2 order(UserData user, int sid, int itemsNumber) {
        dropCart(user, getAddressBySid(sid));
        fillCartOnSid(sid, itemsNumber);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине по определенным координатам
     */
    public OrderV2 order(UserData user, int sid, ZoneV2 coordinates) {
        fillCart(user, sid, coordinates);
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
    public void cancelActiveOrders() {
        List<OrderV2> orders = activeOrders();
        for (OrderV2 order : orders) {
            cancelOrder(order.getNumber());
        }
    }
}
