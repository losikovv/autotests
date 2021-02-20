package instamart.api.helpers;

import instamart.api.action.Authorization;
import instamart.api.action.Departments;
import instamart.api.enums.v2.OrderStatus;
import instamart.api.objects.v1.Offer;
import instamart.api.objects.v2.*;
import instamart.api.requests.ApiV1Requests;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v1.OffersResponse;
import instamart.api.responses.v2.*;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.helpers.WaitingHelper;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;

public final class InstamartApiHelper extends ApiHelperBase {

    private static final Logger log = LoggerFactory.getLogger(InstamartApiHelper.class);

    private final ThreadLocal<Integer> currentSid = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentAddressId = new ThreadLocal<>();
    private final ThreadLocal<String> currentOrderNumber = new ThreadLocal<>();
    private final ThreadLocal<String> currentShipmentNumber = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentShipmentId = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentDeliveryWindowId = new ThreadLocal<>();
    private final ThreadLocal<PaymentTool> currentPaymentTool = new ThreadLocal<>();
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
    private boolean isPointInPolygon(List<Zone> points, Zone point) {
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
    public Zone getInnerPoint(List<Zone> points) {
        List<Double> lats = new ArrayList<>();
        List<Double> lons = new ArrayList<>();

        for (Zone point : points) {
            lats.add(point.getLat());
            lons.add(point.getLon());
        }
        int numberOfTries = 1000;
        double lat = Collections.min(lats);
        double lon = Collections.min(lons);
        double stepLat = (Collections.max(lats) - lat) / numberOfTries;
        double stepLon = (Collections.max(lons) - lon) / numberOfTries;

        Zone point = new Zone(lat, lon);
        for (int i = 0; !isPointInPolygon(points, point) && i < numberOfTries; i++) {
            lat = roundBigDecimal(lat + stepLat,7);
            lon = roundBigDecimal(lon + stepLon,7);

            point.setLat(lat);
            point.setLon(lon);
        }
        return point;
    }

    public boolean authorized() {
        return ApiV2Requests.getToken() != null;
    }

    /**
     * Логаут (чистим токен для авторизации)
     */
    public void logout() {
        if (authorized()) {
            ApiV2Requests.setToken(null);
            log.info("Чистим токен");
        }
    }

    /**
     * Пропускаем тест, если доступен только самовывоз
     * ToDo убрать хардкод данных
     * ToDo если самовывоз, не скипать тест, а оформлять заказ через сайт
     */
    public void skipTestIfOnlyPickupIsAvailable(Store store, String zoneName) {
        List<Integer> pickupRetailers = new ArrayList<>();
        pickupRetailers.add(
                53 //провино
        );
        HashMap<Integer, String> pickupZones = new HashMap<>();
        pickupZones.put(
                104, "Зона #4" //METRO, Воронеж, Семилуки Займище
        );
        if (pickupRetailers.contains(store.getRetailer().getId()) ||
                hashMapContainsIntAndString(store.getId(), zoneName, pickupZones)) {
            throw new SkipException("Доступен только самовывоз\nsid: " + store.getId() + "\n" + zoneName);
        }
    }

    public void skipTestIfOnlyPickupIsAvailable(Store store) {
        skipTestIfOnlyPickupIsAvailable(store, null);
    }

    /*
      МЕТОДЫ ОБРАБОТКИ ОТВЕТОВ API V2
     */

    public List<Taxon> getTaxons(int sid) {
        return ApiV2Requests.Taxons.GET(sid).as(TaxonsResponse.class).getTaxons();
    }

    public Taxon getTaxon(int id, int sid) {
        Response response = ApiV2Requests.Taxons.GET(id, sid);
        assertStatusCode200(response);
        return response.as(TaxonResponse.class).getTaxon();
    }

    public Set<Integer> getTaxonIds(int sid) {
        return getTaxons(sid)
                .stream()
                .map(Taxon::getChildren)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .map(Taxon::getId)
                .collect(Collectors.toSet());
    }

    /**
     * Удаляем товары из корзины
     */
    private void deleteItemsFromCart() {
        Response response = ApiV2Requests.Orders.Shipments.DELETE(currentOrderNumber.get());

        Order order = response.as(OrderResponse.class).getOrder();
        log.info("Удалены все доставки у заказа: {}", order.getNumber());
    }

    /**
     * Изменение/применение параметров адреса из объекта адреса с указанием имени и фамилии юзера
     */
    private void setAddressAttributes(UserData user, Address address) {
        String[] fullName = new String[0];
        if (user.getName() != null) fullName = user.getName().split(" ",2);
        if (fullName.length > 0) address.setFirst_name(fullName[0]);
        if (fullName.length > 1) address.setLast_name(fullName[1]);

        setAddressAttributes(address);
    }

    /**
     * Изменение/применение параматров адреса из объекта адреса
     */
    private void setAddressAttributes(Address address) {
        Response response = ApiV2Requests.Orders.ShipAddressChange.PUT(address, currentOrderNumber.get());

        Address addressFromResponse = response
                .as(ShipAddressChangeResponse.class)
                .getShip_address_change()
                .getOrder()
                .getAddress();
        currentAddressId.set(addressFromResponse.getId());

        log.info("Применен адрес: {}", addressFromResponse);
    }

    /** Получаем список продуктов: по одному из каждой категории */
    public List<Product> getProductFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 1, new SoftAssert());
    }

    /** Получаем список продуктов: максимум (6) из каждой категории */
    public List<Product> getProductsFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 6, new SoftAssert());
    }

    /** Получаем список продуктов: максимум (6) из каждой категории и проверяем корректность категорий */
    public List<Product> getProductsFromEachDepartmentInStore(int sid, SoftAssert softAssert) {
        return getProductsFromEachDepartmentInStore(sid, 6, softAssert);
    }

    /**
     * Получаем список продуктов
     * @param sid сид магазина
     * @param numberOfProductsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    private List<Product> getProductsFromEachDepartmentInStore(
            int sid, int numberOfProductsFromEachDepartment, SoftAssert softAssert) {
        List<Department> departments = Departments.GET(sid, numberOfProductsFromEachDepartment)
                .as(DepartmentsResponse.class).getDepartments();

        String storeUrl = EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + "?sid=" + sid;

        Assert.assertNotEquals(
                departments.size(),
                0,
                "Не импортированы товары для магазина\n" + storeUrl);

        List<Product> products = new ArrayList<>();

        for (Department department : departments) {
            List<Product> productsFromDepartment = department.getProducts();
            if (productsFromDepartment == null) {
                softAssert.fail("\nПоказывается пустая категория " + department.getName() + "\n" + storeUrl);
                continue;
            }
            if (department.getProducts_count() <= 6)
                softAssert.assertEquals(productsFromDepartment.size(), department.getProducts_count(),
                        "\nВ категории " + department.getName() + " отображается "
                                + productsFromDepartment.size() + " товаров, в products_count указано "
                                + department.getProducts_count() + "\n" + storeUrl);

            StringJoiner productsString = new StringJoiner(
                    "\n• ",
                    "Категория: " + department.getName() + "\n• ",
                    "");
            for (Product productFromDepartment : productsFromDepartment) {
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
    public void addItemsToCart(List<Product> products, int quantity) {
        for (Product product : products) {
            addItemToCart(product.getId(), quantity);
        }
    }

    /**
     * Добавляем товар в корзину
     */
    private void addItemToCart(long productId, int quantity) {
        Response response = ApiV2Requests.LineItems.POST(productId, quantity, currentOrderNumber.get());
        assertStatusCode200(response);
        LineItem lineItem = response.as(LineItemResponse.class).getLine_item();

        log.info(lineItem.toString());
    }

    public int getMinOrderAmount(int sid) {
        double minSum = getStore(sid).getMin_order_amount();
        log.info("Минимальная сумма корзины: {}", minSum);
        this.minSum.set((int) minSum);
        return (int) minSum;
    }

    public int getMinFirstOrderAmount(int sid) {
        double minSum = getStore(sid).getMin_first_order_amount();
        log.info("Минимальная сумма корзины: {}", minSum);
        this.minSum.set((int) minSum);
        return (int) minSum;
    }

    /**
     * Получаем id и номер шипмента
     * Получаем минимальную сумму заказа, если сумма не набрана
     */
    private void getMinSumFromAlert() {
        Shipment shipment = ApiV2Requests.Orders.Current.GET()
                .as(OrderResponse.class)
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
    private double getProductWeight(Product product) {
        String humanVolume = product.getHuman_volume();

        if (humanVolume.contains(" шт.")) {
            boolean productHasWeightProperty = false;
            List<Property> properties = ApiV2Requests.Products.GET(product.getId())
                    .as(ProductResponse.class)
                    .getProduct()
                    .getProperties();
            for (Property property : properties) {
                if (property.getName().equalsIgnoreCase("вес")) {
                    humanVolume = property.getValue();
                    productHasWeightProperty = true;
                    break;
                }
            }
            if (!productHasWeightProperty) {
                for (Property property : properties) {
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
        List<ShippingRate> shippingRates = ApiV2Requests.Shipments.ShippingRates
                .GET(currentShipmentNumber
                        .get()).as(ShippingRatesResponse.class).getShipping_rates();

        Assert.assertNotEquals(
                shippingRates.size(),
                0,
                "Нет слотов в магазине " + Pages.Admin.stores(currentSid.get()));

        DeliveryWindow deliveryWindow = shippingRates.get(0).getDelivery_window();

        currentDeliveryWindowId.set(deliveryWindow.getId());

        log.info(deliveryWindow.toString());
    }

    /**
     * Получаем первый доступный способ доставки
     */
    private void getAvailableShippingMethod() {
        List<ShippingMethod> shippingMethods = ApiV2Requests.ShippingMethods.GET(currentSid.get())
                .as(ShippingMethodsResponse.class).getShipping_methods();

        Assert.assertNotEquals(
                shippingMethods.size(),
                0,
                "Нет способов доставки в магазине\n" + Pages.Admin.stores(currentSid.get()));

        ShippingMethod shippingMethod = shippingMethods.get(0);

        currentShipmentMethodId.set(shippingMethod.getId());

        log.info(shippingMethod.toString());
    }

    /**
     * Выбираем id способа оплаты (по умолчанию Картой курьеру)
     */
    private void getAvailablePaymentTool() {
        List<PaymentTool> paymentTools = ApiV2Requests.PaymentTools.GET().as(PaymentToolsResponse.class).getPayment_tools();

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
        Response response = ApiV2Requests.Orders.PUT(
                //currentAddressId.get(), //параметр ломает оформление заказа в некоторых магазинах
                1,
                "+7 (987) 654 32 10",
                "test",
                currentPaymentTool.get().getId(),
                currentShipmentId.get(),
                currentDeliveryWindowId.get(),
                currentShipmentMethodId.get(),
                currentOrderNumber.get());
        assertStatusCode200(response);
        Order order = response.as(OrderResponse.class).getOrder();
        log.info("Применены атрибуты для заказа: {}", order.getNumber());
        log.info("        full_address: {}", order.getAddress().getFull_address());
        log.info("  replacement_policy: {}", order.getReplacement_policy().getDescription());
        log.info("  delivery_starts_at: {}", order.getShipments().get(0).getDelivery_window().getStarts_at());
        log.info("    delivery_ends_at: {}", order.getShipments().get(0).getDelivery_window().getEnds_at());
        log.info("special_instructions: {}", order.getSpecial_instructions());
    }

    /**
     * Завершаем оформление заказа
     */
    private Order completeOrder() {
        orderCompleted.set(false);
        Response response = ApiV2Requests.Orders.Completion.POST(currentOrderNumber.get());
        if (response.getStatusCode() == 422) {
            Errors errors = response.as(ErrorResponse.class).getErrors();

            if (errors.getShipments() != null) {
                String notAvailableDeliveryWindow = "Выбранный интервал стал недоступен";
                if (errors.getShipments().contains(notAvailableDeliveryWindow)) {
                    log.error(notAvailableDeliveryWindow);
                    getAvailableDeliveryWindow();
                    setDefaultOrderAttributes();
                    response = ApiV2Requests.Orders.Completion.POST(currentOrderNumber.get());
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
        if (response.as(OrderResponse.class).getOrder() != null) {
            Order order = response.as(OrderResponse.class).getOrder();
            if (order.getShipments().get(0).getAlerts().size() > 0) {
                log.info(order.getShipments().get(0).getAlerts().get(0).getFull_message());
            }
            log.info("Оформлен заказ: {}", order.getNumber());
            orderCompleted.set(true);
            return response.as(OrderResponse.class).getOrder();
        }
        return null;
    }

    /**
     * Отменяем заказ по номеру
     */
    private void cancelOrder(String orderNumber) {
        Response response = ApiV2Requests.Orders.Cancellations.POST(orderNumber, "test");
        assertStatusCode200(response);
        Order order = response.as(CancellationsResponse.class).getCancellation().getOrder();
        log.info("Отменен заказ: {}", order.getNumber());
    }

    /**
     * Выбираем первый доступный магазин (берем координаты из обьекта необходимого адреса)
     */
    private void getCurrentSid(Address address) {
        getCurrentSid(address.getLat(), address.getLon());
    }

    /**
     * Выбираем первый доступный магазин по координатам
     */
    private void getCurrentSid(double lat, double lon) {
        Store store = availableStores(lat, lon).get(0);

        currentSid.set(store.getId());

        log.info("Выбран магазин: {}", store);
    }

    /**
     * Выбираем первый доступный магазин определенного ритейлера (берем координаты из обьекта необходимого адреса)
     */
    private void getCurrentSid(Address address, String retailer) {
        if (address.getLat() == null || address.getLon() == null)
            throw new NullPointerException("Не указаны координаты");
        getCurrentSid(address.getLat(), address.getLon(), retailer);
    }

    /**
     * Выбираем первый доступный магазин определенного ритейлера по координатам
     */
    private void getCurrentSid(double lat, double lon, String retailer) {
        List<Store> stores = availableStores(lat, lon);

        for (Store store : stores) {
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
    private Address getAddressBySid(int sid) {
        currentSid.set(sid);
        Response response = ApiV2Requests.Stores.GET(sid);

        if (response.statusCode() == 422)
            if (response.as(ErrorResponse.class)
                    .getErrors()
                    .getBase()
                    .contains("По указанному адресу"))
                Assert.fail("Магазин отключен " + Pages.Admin.stores(currentSid.get()));

        Store store = response.as(StoreResponse.class).getStore();

        Address address = store.getLocation();
        log.info("Получен адрес {}", address.getFull_address());

        List<List<Zone>> zones = store.getZones();
        Zone zone = getInnerPoint(zones.get(zones.size() - 1));
        address.setCoordinates(zone);
        log.info("Выбраны координаты: {}", zone);

        return address;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public List<Retailer> availableRetailers() {
        List<Retailer> retailers = ApiV2Requests.Retailers.GET().as(RetailersResponse.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (Retailer retailer : retailers) {
            availableRetailers.add(retailer.toString());
        }
        log.info(availableRetailers.toString());

        return retailers;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public List<Retailer> availableRetailersSpree() {
        List<Retailer> retailers = ApiV1Requests.Retailers.GET().as(RetailersResponse.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (Retailer retailer : retailers)
            if (retailer.getAvailable()) availableRetailers.add(retailer.toString());
        log.info(availableRetailers.toString());

        StringJoiner notAvailableRetailers = new StringJoiner(
                "\n• ",
                "Список неактивных ретейлеров:\n• ",
                "\n");
        for (Retailer retailer : retailers)
            if (!retailer.getAvailable()) notAvailableRetailers.add(retailer.toString());
        log.info(notAvailableRetailers.toString());

        return retailers;
    }

    /**
     * Получить список активных магазинов как список объектов Store (без зон доставки)
     */
    public List<Store> availableStores() {
        List<Retailer> retailers = availableRetailers();
        List<Store> stores = new ArrayList<>();

        for (Retailer retailer : retailers) {
            List<Store> retailerStores = ApiV1Requests.Retailers.Stores.GET(retailer.getId()).as(StoresResponse.class).getStores();
            for (Store retailerStore: retailerStores) {
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
    public List<Store> availableStores(Retailer retailer) {
        List<Store> stores = ApiV1Requests.Retailers.Stores.GET(retailer.getId()).as(StoresResponse.class).getStores();
        for (Store store : stores) {
            store.setRetailer(retailer);
        }
        printAvailableStores(stores);

        return stores;
    }

    /**
     * Получить по координатам список активных магазинов как список объектов Store
     */
    private List<Store> availableStores(double lat, double lon) {
        List<Store> stores = ApiV2Requests.Stores.GET(lat, lon).as(StoresResponse.class).getStores();

        if (stores.size() > 0) {
            printAvailableStores(stores);
        } else {
            log.error("По выбранному адресу нет магазинов");
        }

        return stores;
    }

    private void printAvailableStores(List<Store> stores) {
        StringJoiner availableStores = new StringJoiner(
                "\n• ",
                "Список активных магазинов:\n• ",
                "\n");
        for (Store store : stores) {
            availableStores.add(store.toString());
        }
        log.info(availableStores.toString());
    }

    /**
     * Получить список зон доставки магазина
     */
    public List<List<Zone>> storeZones(int sid) {
        return ApiV2Requests.Stores.GET(sid).as(StoreResponse.class).getStore().getZones();
    }

    public Store getStore(int sid) {
        return ApiV2Requests.Stores.GET(sid).as(StoreResponse.class).getStore();
    }

    /**
     * Авторизация
     * На сервере стоит ограничение - не больше 20 авторизаций в минуту с одного IP,
     * поэтому для мультипоточности реализованы synchronized + ожидание 3.1 секунды
     */
    synchronized public void authorisation(String email, String password) {
        WaitingHelper.simply(3.1);
        final Response response = Authorization.auth(email, password);
        assertStatusCode200(response);
        ApiV2Requests.setToken(response
                .as(SessionsResponse.class)
                .getSession()
                .getAccess_token());
        log.info("Авторизуемся: {} / {}", email, password);
        log.info("access_token: {}", ApiV2Requests.getToken());
    }

    /**
     * Авторизация с данными из объекта юзера
     */
    public void authorisation(UserData user) {
        authorisation(user.getLogin(), user.getPassword());
    }

    /**
     * Узнаем номер заказа
     */
    public String getCurrentOrderNumber() {
        Response response = ApiV2Requests.Orders.POST();
        assertStatusCode200(response);
        currentOrderNumber.set(response
                .as(OrderResponse.class)
                .getOrder()
                .getNumber());
        log.info("Номер текущего заказа: {}", currentOrderNumber.get());
        return currentOrderNumber.get();
    }

    /**
     * Получаем список активных (принят, собирается, в пути) заказов
     */
    private List<Order> activeOrders() {
        OrdersResponse response = ApiV2Requests.Orders.GET(OrderStatus.ACTIVE).as(OrdersResponse.class);
        List<Order> orders = response.getOrders();

        if (orders.size() < 1) {
            log.warn("Нет активных заказов");
        } else {
            int pages = response.getMeta().getTotal_pages();
            if (pages > 1) {
                for (int i = 2; i <= pages; i++) {
                    orders.addAll(ApiV2Requests.Orders.GET(OrderStatus.ACTIVE, i).as(OrdersResponse.class).getOrders());
                }
            }
            log.info("Список активных заказов:");
            for (Order order : orders) {
                log.info(order.getNumber());
            }
        }
        return orders;
    }

    public List<Offer> getOffers(String storeUuid) {
        return ApiV1Requests.Stores.Offers.GET(
                storeUuid,
                "вода",
                "")
                .as(OffersResponse.class).getOffers();
    }

    /*
      МЕТОДЫ ИЗ НЕСКОЛЬКИХ ЗАПРОСОВ
     */

    /**
     * Наполняем корзину разными товарами до минимальной суммы заказа в конкретном магазине
     */
    private void fillCartOnSid(int sid, int items) {
        List<Product> products = getProductFromEachDepartmentInStore(sid);
        double initialCartWeight = 0;
        for (int i = 0; i < items; i++) {
            Product product = products.get(i);
            addItemToCart(product.getId(),1);
            initialCartWeight += (getProductWeight(product) * product.getItems_per_pack());
        }
        getMinSumFromAlert();
        int quantity = 1;
        for (Product product : products) {
            if (minSumNotReached.get())
                quantity = (int) Math.ceil(minSum.get() / (product.getPrice() * product.getItems_per_pack()));

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
    private Order setDefaultAttributesAndCompleteOrder() {
        getAvailablePaymentTool();
        getAvailableShippingMethod();
        getAvailableDeliveryWindow();

        setDefaultOrderAttributes();
        return completeOrder();
    }

    /**
     * Поменять адрес у юзера
     */
    public void setAddress(UserData user, Address address) {
        authorisation(user);
        getCurrentOrderNumber();

        setAddressAttributes(address);
    }

    /**
     * Наполнить корзину и выбрать адрес по умолчанию у юзера по определенному адресу
     */
    public void fillCart(UserData user, Address address) {
        dropCart(user, address);

        getCurrentSid(address);
        fillCartOnSid(currentSid.get());
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера по определенному адресу у определенного ритейлера
     */
    public void fillCart(UserData user, Address address, String retailer) {
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
    public void fillCart(UserData user, int sid, Zone coordinates) {
        Address address = getAddressBySid(sid);
        address.setCoordinates(coordinates);

        dropCart(user, address);

        fillCartOnSid(sid);
    }

    /**
     * Очистить корзину и выбрать адрес у юзера
     */
    public void dropCart(UserData user, Address address) {
        authorisation(user);
        getCurrentOrderNumber();
        deleteItemsFromCart();

        setAddressAttributes(user, address);
    }

    /**
     * Оформить тестовый заказ у юзера по определенному адресу
     */
    public Order order(UserData user, Address address, String retailer) {
        fillCart(user, address, retailer);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    public Order order(UserData user, int sid) {
        fillCart(user, sid);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    public Order order(UserData user, int sid, int itemsNumber) {
        dropCart(user, getAddressBySid(sid));
        fillCartOnSid(sid, itemsNumber);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине по определенным координатам
     */
    public Order order(UserData user, int sid, Zone coordinates) {
        fillCart(user, sid, coordinates);
        return setDefaultAttributesAndCompleteOrder();
    }

    /**
     * Отменить последний заказ (с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelCurrentOrder() {
        if (authorized() && currentOrderNumber.get() != null && orderCompleted.get() != null && orderCompleted.get())
            cancelOrder(currentOrderNumber.get());
    }

    /**
     * Отменить активные (принят, собирается, в пути) заказы
     */
    public void cancelActiveOrders() {
        List<Order> orders = activeOrders();
        for (Order order : orders) {
            cancelOrder(order.getNumber());
        }
    }
}
