package instamart.api.helpers;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.objects.v2.*;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.*;
import instamart.core.common.AppManager;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.asserts.SoftAssert;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static instamart.ui.modules.Base.kraken;

public class ApiV2Helper extends HelperBase {
    private final ThreadLocal<Integer> currentSid = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentAddressId = new ThreadLocal<>();
    public final ThreadLocal<String> currentOrderNumber = new ThreadLocal<>();
    private final ThreadLocal<String> currentShipmentNumber = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentShipmentId = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentDeliveryWindowId = new ThreadLocal<>();
    private final ThreadLocal<PaymentTool> currentPaymentTool = new ThreadLocal<>();
    private final ThreadLocal<Integer> currentShipmentMethodId = new ThreadLocal<>();
    private final ThreadLocal<Integer> minSum = new ThreadLocal<>();
    private final ThreadLocal<Boolean> minSumNotReached = new ThreadLocal<>();
    private final ThreadLocal<Boolean> productWeightNotDefined = new ThreadLocal<>();
    private final ThreadLocal<Boolean> orderCompleted = new ThreadLocal<>();

    public ApiV2Helper() {
    }

    /**
     * Округляем double до определенного количества символов после запятой
     */
    private static double roundBigDecimal(double value, int decimal) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(decimal, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Проверяем, попадают ли координаты в зону доставки
     */
    private static boolean isPointInPolygon(List<Zone> points, Zone point) {
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
    public static Zone getInnerPoint(List<Zone> points) {
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
            lat = roundBigDecimal(lat + stepLat,6);
            lon = roundBigDecimal(lon + stepLon,6);

            point.setLat(lat);
            point.setLon(lon);
        }
        return point;
    }

    public boolean authorized() {
        return ApiV2Requests.token.get() != null;
    }

    /**
     * Логаут (чистим токен для авторизации)
     */
    public void logout() {
        if (authorized()) {
            ApiV2Requests.token.set(null);
            System.out.println("Чистим токен");
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
        if (listContainsInt(store.getRetailer().getId(), pickupRetailers) ||
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

    public static List<Taxon> getCategories(int sid) {
        List<Taxon> taxons = ApiV2Requests.getTaxons(sid).as(TaxonsResponse.class).getTaxons();
        System.out.println(taxons);
        return taxons;
    }

    public Taxon getCategory(int id, int sid) {
        Taxon taxon = ApiV2Requests.getTaxons(id, sid).as(TaxonResponse.class).getTaxon();
        System.out.println(taxon);
        return taxon;
    }

    /**
     * Удаляем товары из корзины
     */
    private void deleteItemsFromCart() {
        Response response = ApiV2Requests.deleteShipments(currentOrderNumber.get());

        Order order = response.as(OrderResponse.class).getOrder();
        System.out.println("Удалены все доставки у заказа: " + order.getNumber() + "\n");
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
        Response response = ApiV2Requests.putShipAddressChange(address, currentOrderNumber.get());

        Address addressFromResponse = response
                .as(ShipAddressChangeResponse.class)
                .getShip_address_change()
                .getOrder()
                .getAddress();
        currentAddressId.set(addressFromResponse.getId());

        printSuccess("Применен адрес: " + addressFromResponse);
    }

    /** Получаем список продуктов: по одному из каждой категории */
    public static List<Product> getProductFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 1, new SoftAssert());
    }

    /** Получаем список продуктов: максимум (6) из каждой категории */
    public static List<Product> getProductsFromEachDepartmentInStore(int sid) {
        return getProductsFromEachDepartmentInStore(sid, 6, new SoftAssert());
    }

    /** Получаем список продуктов: максимум (6) из каждой категории и проверяем корректность категорий */
    public static List<Product> getProductsFromEachDepartmentInStore(int sid, SoftAssert softAssert) {
        return getProductsFromEachDepartmentInStore(sid, 6, softAssert);
    }

    /**
     * Получаем список продуктов
     * @param sid сид магазина
     * @param numberOfProductsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    private static List<Product> getProductsFromEachDepartmentInStore(
            int sid, int numberOfProductsFromEachDepartment, SoftAssert softAssert) {
        List<Department> departments = ApiV2Requests.getDepartments(sid, numberOfProductsFromEachDepartment)
                .as(DepartmentsResponse.class).getDepartments();

        String storeUrl = AppManager.environment.getBasicUrlWithHttpAuth() + "?sid=" + sid;

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
                    "\n");
            for (Product productFromDepartment : productsFromDepartment) {
                products.add(productFromDepartment);
                productsString.add(productFromDepartment.toString());
            }
            System.out.println(productsString);
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
        Response response = ApiV2Requests.postLineItems(productId, quantity, currentOrderNumber.get());
        ApiV2Checkpoints.assertStatusCode200(response);
        LineItem lineItem = response.as(LineItemResponse.class).getLine_item();

        printSuccess(lineItem.toString());
    }

    /**
     * Получаем id и номер шипмента
     * Получаем минимальную сумму заказа, если сумма не набрана
     */
    private void getMinSum() {
        Shipment shipment = ApiV2Requests.getOrdersCurrent()
                .as(OrderResponse.class)
                .getOrder()
                .getShipments()
                .get(0);
        if (shipment.getAlerts().size() > 0) {
            String alertMessage = shipment.getAlerts().get(0).getMessage().replaceAll("[^0-9]","");
            minSum.set(Integer.parseInt(alertMessage.substring(0, alertMessage.length() - 2)));
            printSuccess("Минимальная сумма корзины: " + minSum.get());
            minSumNotReached.set(true);
        } else {
            printSuccess("Минимальная сумма корзины набрана");
            minSumNotReached.set(false);
        }
        currentShipmentId.set(shipment.getId());
        currentShipmentNumber.set(shipment.getNumber());
        System.out.println("Номер доставки: " + currentShipmentNumber.get() + "\n");
    }

    /**
     * Узнаем вес продукта, полученного через GET v2/departments
     */
    private double getProductWeight(Product product) {
        String humanVolume = product.getHuman_volume();

        if (humanVolume.contains(" шт.")) {
            boolean productHasWeightProperty = false;
            List<Property> properties = ApiV2Requests.getProducts(product.getId())
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
            System.out.println(product + "\nВес продукта: " + productWeight + " кг.");
            productWeightNotDefined.set(false);
            return productWeight;
        } else if (humanVolume.contains(" г") || humanVolume.contains(" мл")) {
            System.out.println(product + "\nВес продукта: " + productWeight / 1000 + " кг.");
            productWeightNotDefined.set(false);
            return productWeight / 1000;
        } else {
            printError(product + "\nНеизвестный тип веса/объема: " + humanVolume);
            productWeightNotDefined.set(true);
            return 0;
        }
    }

    /**
     * Получаем первый доступный слот
     */
    private void getAvailableDeliveryWindow() {
        List<ShippingRate> shippingRates = ApiV2Requests
                .getShippingRates(currentShipmentNumber
                        .get()).as(ShippingRatesResponse.class).getShipping_rates();

        Assert.assertNotEquals(
                shippingRates.size(),
                0,
                "Нет слотов в магазине\n" + Pages.Admin.stores(currentSid.get()));

        DeliveryWindow deliveryWindow = shippingRates.get(0).getDelivery_window();

        currentDeliveryWindowId.set(deliveryWindow.getId());

        System.out.println(deliveryWindow);
    }

    /**
     * Получаем первый доступный способ доставки
     */
    private void getAvailableShippingMethod() {
        List<ShippingMethod> shippingMethods = ApiV2Requests.getShippingMethod(currentSid.get())
                .as(ShippingMethodsResponse.class).getShipping_methods();

        Assert.assertNotEquals(
                shippingMethods.size(),
                0,
                "Нет способов доставки в магазине\n" + Pages.Admin.stores(currentSid.get()));

        ShippingMethod shippingMethod = shippingMethods.get(0);

        currentShipmentMethodId.set(shippingMethod.getId());

        System.out.println(shippingMethod);
    }

    /**
     * Выбираем id способа оплаты (по умолчанию Картой курьеру)
     */
    private void getAvailablePaymentTool() {
        List<PaymentTool> paymentTools = ApiV2Requests.getPaymentTools().as(PaymentToolsResponse.class).getPayment_tools();

        StringJoiner availablePaymentTools = new StringJoiner(
                "\n• ",
                "Список доступных способов оплаты:\n• ",
                "\n");
        boolean cardCourier = false;
        for (int i = 0; i < paymentTools.size(); i++) {
            String selectedPaymentTool = greenText(paymentTools.get(i) + " <<< Выбран");
            if (paymentTools.get(i).getName().equalsIgnoreCase("Картой курьеру")) {
                currentPaymentTool.set(paymentTools.get(i));
                availablePaymentTools.add(selectedPaymentTool);
                cardCourier = true;
            } else if (i == paymentTools.size() - 1 && !cardCourier) { // выбираем последний способ, если нет картой курьеру
                currentPaymentTool.set(paymentTools.get(i));
                availablePaymentTools.add(selectedPaymentTool);
            } else availablePaymentTools.add(paymentTools.get(i).toString());
        }
        System.out.println(availablePaymentTools);
    }

    /**
     * Применяем дефолтные параметры к заказу
     */
    private void setDefaultOrderAttributes() {
        Response response = ApiV2Requests.putOrders(
                //currentAddressId.get(), //параметр ломает оформление заказа в некоторых магазинах
                1,
                "+7 (987) 654 32 10",
                "test",
                currentPaymentTool.get().getId(),
                currentShipmentId.get(),
                currentDeliveryWindowId.get(),
                currentShipmentMethodId.get(),
                currentOrderNumber.get());
        ApiV2Checkpoints.assertStatusCode200(response);
        Order order = response.as(OrderResponse.class).getOrder();
        printSuccess("Применены атрибуты для заказа: " + order.getNumber());
        System.out.println("        full_address: " + order.getAddress().getFull_address());
        System.out.println("  replacement_policy: " + order.getReplacement_policy().getDescription());
        System.out.println("  delivery_starts_at: " + order.getShipments().get(0).getDelivery_window().getStarts_at());
        System.out.println("    delivery_ends_at: " + order.getShipments().get(0).getDelivery_window().getEnds_at());
        System.out.println("special_instructions: " + order.getSpecial_instructions() + "\n");
    }

    /**
     * Завершаем оформление заказа
     */
    private Order completeOrder() {
        orderCompleted.set(false);
        Response response = ApiV2Requests.postOrdersCompletion(currentOrderNumber.get());
        if (response.getStatusCode() == 422) {
            Errors errors = response.as(ErrorResponse.class).getErrors();

            if (errors.getShipments() != null) {
                String notAvailableDeliveryWindow = "Выбранный интервал стал недоступен";
                if (errors.getShipments().contains(notAvailableDeliveryWindow)) {
                    printError(notAvailableDeliveryWindow);
                    System.out.println();
                    getAvailableDeliveryWindow();
                    setDefaultOrderAttributes();
                    response = ApiV2Requests.postOrdersCompletion(currentOrderNumber.get());
                } else Assert.fail(response.body().toString());
            }
            if (errors.getPayments() != null) {
                String notAvailablePaymentMethod = "Заказ не может быть оплачен указанным способом";
                if (errors.getPayments().contains(notAvailablePaymentMethod)) {
                    printError("\n" + notAvailablePaymentMethod + "\n" + currentPaymentTool.get() + "\n");
                    //ToDo помечать тест желтым, если заказ не может быть оплачен указанным способом
                    return null;
                } else Assert.fail(response.body().toString());
            }
        }
        if (response.as(OrderResponse.class).getOrder() != null) {
            Order order = response.as(OrderResponse.class).getOrder();
            if (order.getShipments().get(0).getAlerts().size() > 0) {
                System.out.println(order.getShipments().get(0).getAlerts().get(0).getFull_message());
            }
            printSuccess("Оформлен заказ: " + order.getNumber() + "\n");
            orderCompleted.set(true);
            return response.as(OrderResponse.class).getOrder();
        }
        return null;
    }

    /**
     * Отменяем заказ по номеру
     */
    private void cancelOrder(String number) {
        Response response = ApiV2Requests.postOrdersCancellations(number);
        ApiV2Checkpoints.assertStatusCode200(response);
        Order order = response.as(CancellationsResponse.class).getCancellation().getOrder();
        printSuccess("Отменен заказ: " + order.getNumber() + "\n");
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

        printSuccess("Выбран магазин: " + store);
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

                printSuccess("Выбран магазин: " + store + "\n");
                return;
            }
        }
        printError("По выбранному адресу нет ретейлера " + retailer + "\n");
    }

    /**
     * Получить адрес доставки, зная только sid
     */
    private Address getAddressBySid(int sid) {
        currentSid.set(sid);
        Response response = ApiV2Requests.getStores(sid);

        if (response.statusCode() == 422)
            if (response.as(ErrorResponse.class)
                    .getErrors()
                    .getBase()
                    .contains("По указанному адресу"))
                Assert.fail(sid + " sid отключен");

        Store store = response.as(StoreResponse.class).getStore();

        Address address = store.getLocation();
        System.out.println("Получен адрес " + address.getFull_address());

        List<List<Zone>> zones = store.getZones();
        Zone zone = getInnerPoint(zones.get(zones.size() - 1));
        address.setCoordinates(zone);
        System.out.println("Выбраны координаты: " + zone + "\n");

        return address;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public static List<Retailer> availableRetailers() {
        List<Retailer> retailers = ApiV2Requests.getRetailers().as(RetailersResponse.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (Retailer retailer : retailers) {
            availableRetailers.add(retailer.toString());
        }
        System.out.println(availableRetailers);

        return retailers;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public static List<Retailer> availableRetailersSpree() {
        List<Retailer> retailers = ApiV2Requests.getRetailersSpree().as(RetailersResponse.class).getRetailers();

        StringJoiner availableRetailers = new StringJoiner(
                "\n• ",
                "Список активных ретейлеров:\n• ",
                "\n");
        for (Retailer retailer : retailers)
            if (retailer.getAvailable()) availableRetailers.add(retailer.toString());
        System.out.println(availableRetailers);

        StringJoiner notAvailableRetailers = new StringJoiner(
                "\n• ",
                "Список неактивных ретейлеров:\n• ",
                "\n");
        for (Retailer retailer : retailers)
            if (!retailer.getAvailable()) notAvailableRetailers.add(retailer.toString());
        System.out.println(notAvailableRetailers);

        return retailers;
    }

    /**
     * Получить список активных магазинов как список объектов Store (без зон доставки)
     */
    public static List<Store> availableStores() {
        List<Retailer> retailers = availableRetailers();
        List<Store> stores = new ArrayList<>();

        for (Retailer retailer : retailers) {
            List<Store> retailerStores = ApiV2Requests.getRetailerStoresSpree(retailer.getId()).as(StoresResponse.class).getStores();
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
    public static List<Store> availableStores(Retailer retailer) {
        List<Store> stores = ApiV2Requests.getRetailerStoresSpree(retailer.getId()).as(StoresResponse.class).getStores();
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
        List<Store> stores = ApiV2Requests.getStores(lat, lon).as(StoresResponse.class).getStores();

        if (stores.size() > 0) {
            printAvailableStores(stores);
        } else {
            printError("По выбранному адресу нет магазинов");
        }

        return stores;
    }

    private static void printAvailableStores(List<Store> stores) {
        StringJoiner availableStores = new StringJoiner(
                "\n• ",
                "Список активных магазинов:\n• ",
                "\n");
        for (Store store : stores) {
            availableStores.add(store.toString());
        }
        System.out.println(availableStores);
    }

    /**
     * Получить список зон доставки магазина
     */
    public static List<List<Zone>> storeZones(int sid) {
        return ApiV2Requests.getStores(sid).as(StoreResponse.class).getStore().getZones();
    }

    public String getStoreName(int sid) {
        return ApiV2Requests.getStores(sid).as(StoreResponse.class).getStore().getName();
    }

    /**
     * Авторизация
     * На сервере стоит ограничение - не больше 20 авторизаций в минуту с одного IP,
     * поэтому для мультипоточности реализованы synchronized + ожидание 3.1 секунды
     */
    synchronized public void authorisation(String email, String password) {
        kraken.await().simply(3.1);
        Response response = ApiV2Requests.postSessions(email, password);
        ApiV2Checkpoints.assertStatusCode200(response);
        ApiV2Requests.token.set(response
                .as(SessionsResponse.class)
                .getSession()
                .getAccess_token());
        System.out.println("Авторизуемся: " + email + " / " + password);
        System.out.println("access_token: " + ApiV2Requests.token.get() + "\n");
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
        currentOrderNumber.set(ApiV2Requests.postOrder()
                .as(OrderResponse.class)
                .getOrder()
                .getNumber());
        System.out.println("Номер текущего заказа: " + currentOrderNumber.get() + "\n");
        return currentOrderNumber.get();
    }

    /**
     * Получаем список активных (принят, собирается, в пути) заказов
     */
    private List<Order> activeOrders() {
        OrdersResponse response = ApiV2Requests.getActiveOrders().as(OrdersResponse.class);
        List<Order> orders = response.getOrders();

        if (orders.size() < 1) {
            System.out.println("Нет активных заказов");
        } else {
            int pages = response.getMeta().getTotal_pages();
            if (pages > 1) {
                for (int i = 2; i <= pages; i++) {
                    orders.addAll(ApiV2Requests.getActiveOrders(i).as(OrdersResponse.class).getOrders());
                }
            }
            System.out.println("Список активных заказов:");
            for (Order order : orders) {
                System.out.println(order.getNumber());
            }
            System.out.println();
        }
        return orders;
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
        getMinSum();
        int quantity = 1;
        for (Product product : products) {
            if (minSumNotReached.get())
                quantity = (int) Math.ceil(minSum.get() / (product.getPrice() * product.getItems_per_pack()));

            double finalCartWeight = roundBigDecimal(
                    (getProductWeight(product) * quantity) + initialCartWeight, 3);

            String cartWeightText = "Вес корзины: " + finalCartWeight + " кг.\n";
            String anotherProductText = "Выбираем другой товар\n";
            if (finalCartWeight > 40) printError(cartWeightText + anotherProductText);
            else if (productWeightNotDefined.get()) printError(anotherProductText);
            else {
                printSuccess(cartWeightText);
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

    /*
      МЕТОДЫ ДЛЯ ИСПОЛЬЗОВАНИЯ В ТЕСТАХ (ПУБЛИЧНЫЕ)
     */

    /**
     * Регистрация
     */
    public void registration(UserData user) {
        String[] fullName = new String[0];
        String firstName = null;
        String lastName = null;
        if (user.getName() != null) fullName = user.getName().split(" ",2);
        if (fullName.length > 0) firstName = fullName[0];
        if (fullName.length > 1) lastName = fullName[1];

        registration(
                user.getLogin(),
                firstName,
                lastName,
                user.getPassword());
    }

    /**
     * Регистрация
     */
    public void registration(String email, String firstName, String lastName, String password) {
        Response response =  ApiV2Requests.postUsers(email, firstName, lastName, password);
        ApiV2Checkpoints.assertStatusCode200(response);
        String registeredEmail = response
                .as(UsersResponse.class)
                .getUser()
                .getEmail();
        printSuccess("Зарегистрирован: " + registeredEmail + "\n");
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
        if (currentOrderNumber.get() != null && orderCompleted.get())
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
