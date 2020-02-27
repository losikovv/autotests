package ru.instamart.application.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.objects.*;
import ru.instamart.application.rest.objects.responses.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static org.hamcrest.Matchers.lessThan;

public class RestHelper extends Requests {
    private static String fullBaseUrl;
    private int currentSid;
    private int currentAddressId;
    private int currentShipmentId;
    private int currentDeliveryWindowId;
    private int currentPaymentToolId;
    private int currentShipmentMethodId;
    private int minSum;
    private boolean minSumNotReached;
    private boolean productWeightNotDefined;

    public RestHelper(EnvironmentData environment) {
        fullBaseUrl = environment.getBasicUrlWithHttpAuth();
        baseURI = fullBaseUrl.substring(0, fullBaseUrl.length() - 1);
        basePath = "api/";
        port = 443;
        config = config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        defaultParser = Parser.JSON;

        requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .addFilter(new ErrorLoggingFilter())
                .build();

        responseSpecification = new ResponseSpecBuilder()
                .expectResponseTime(lessThan(30000L))
                .build();
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
    static Zone getInnerPoint(List<Zone> points) {
        List<Double> lats = new ArrayList<>();
        List<Double> lons = new ArrayList<>();

        for (Zone point : points) {
            lats.add(point.getLat());
            lons.add(point.getLon());
        }
        int numberOfTries = 100;
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

    /**
     * Зеленый текст
     */
    static void printSuccess(Object string) {
        System.out.println(greenText(string));
    }

    private static String greenText(Object string) {
        return "\u001b[32m" + string.toString() + "\u001B[0m";
    }

    /**
     * Красный текст
     */
    static void printError(Object string) {
        System.out.println(redText(string));
    }

    private static String redText(Object string) {
        return "\033[0;91m" + string.toString() + "\u001B[0m";
    }

    /*
      МЕТОДЫ ОБРАБОТКИ ОТВЕТОВ REST API (ПРИВАТНЫЕ)
     */

    /**
     * Удаляем товары из корзины
     */
    private void deleteItemsFromCart() {
        String orderNumber = deleteShipments()
                .as(OrdersResponse.class)
                .getOrder()
                .getNumber();
        System.out.println("Удалены все доставки у заказа: " + orderNumber + "\n");
    }

    /**
     * Изменение/применение параматров адреса из объекта адреса с указанием имени и фамилии юзера
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
        Address addressFromResponse = putShipAddressChange(address)
                .as(ShipAddressChangeResponse.class)
                .getShip_address_change()
                .getOrder()
                .getAddress();
        currentAddressId = addressFromResponse.getId();

        printSuccess("Применен адрес: " + addressFromResponse);
    }

    /**
     * Получаем любой продукт (1-й продукт из 1-й категории) для оформления заказа
     */
    private Product getFirstProductFromStore(int sid) {
        return getProductsFromEachDepartmentInStore(
                sid,
                1,
                true)
                .get(0);
    }

    /**
     * Получаем список продуктов
     * @param sid сид магазина
     * @param numberOfProductsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    public static List<Product> getProductsFromEachDepartmentInStore(
            int sid, int numberOfProductsFromEachDepartment, boolean catchExceptions) {
        SoftAssert softAssert = new SoftAssert();
        List<Department> departments = getDepartments(sid, numberOfProductsFromEachDepartment).as(DepartmentsResponse.class).getDepartments();

        String storeUrl = fullBaseUrl + "?sid=" + sid;

        Assert.assertNotEquals(
                departments.size(),
                0,
                "Не импортированы товары для магазина\n" + storeUrl);

        List<Product> products = new ArrayList<>();

        for (Department department : departments) {
            List<Product> productsFromDepartment = department.getProducts();
            if (productsFromDepartment == null) {
                softAssert.fail("Показывается пустая категория " + department.getName() + "\n" + storeUrl);
                continue;
            }
            if (department.getProducts_count() <= 6)
                softAssert.assertEquals(productsFromDepartment.size(), department.getProducts_count(),
                        "В категории " + department.getName() + " отображается "
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
        if (!catchExceptions) softAssert.assertAll();
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
        LineItem lineItem = postLineItems(productId, quantity).as(LineItemResponse.class).getLine_item();

        printSuccess(lineItem);
    }

    /**
     * Получаем id и номер шипмента
     * Получаем минимальную сумму заказа, если сумма не набрана
     */
    private void getMinSum() {
        Shipment shipment = getOrdersCurrent()
                .as(OrdersResponse.class)
                .getOrder()
                .getShipments()
                .get(0);
        if (shipment.getAlerts().size() > 0) {
            String alertMessage = shipment.getAlerts().get(0).getMessage().replaceAll("[^0-9]","");
            minSum = Integer.parseInt(alertMessage.substring(0, alertMessage.length() - 2));
            printSuccess("Минимальная сумма корзины: " + minSum);
            minSumNotReached = true;
        } else {
            printSuccess("Минимальная сумма корзины набрана");
            minSumNotReached = false;
        }
        currentShipmentId = shipment.getId();
        currentShipmentNumber = shipment.getNumber();
        System.out.println("Номер доставки: " + currentShipmentNumber + "\n");
    }

    /**
     * Узнаем вес продукта, полученного через GET v2/departments
     */
    private double getProductWeight(Product product) {
        String humanVolume = product.getHuman_volume();

        if (humanVolume.contains(" шт.")) {
            List<Property> properties = getProducts(product.getId())
                    .as(ProductResponse.class)
                    .getProduct()
                    .getProperties();
            for (Property property : properties) {
                if (property.getName().equalsIgnoreCase("вес") ||
                        property.getName().equalsIgnoreCase("объем")) {
                    humanVolume = property.getValue();
                    break;
                }
            }
        }
        double productWeight = Double.parseDouble((humanVolume.split(" ")[0]).replace(",","."));

        if (humanVolume.contains(" кг") || humanVolume.contains(" л")) {
            System.out.println(product + "\nВес продукта: " + productWeight + " кг.");
            productWeightNotDefined = false;
            return productWeight;
        } else if (humanVolume.contains(" г") || humanVolume.contains(" мл")) {
            System.out.println(product + "\nВес продукта: " + productWeight / 1000 + " кг.");
            productWeightNotDefined = false;
            return productWeight / 1000;
        } else {
            System.out.println(product + "\nНеизвестный тип веса/объема: " + humanVolume);
            productWeightNotDefined = true;
            return 0;
        }
    }

    /**
     * Получаем первый доступный слот
     */
    private void getAvailableDeliveryWindow() {
        List<ShippingRate> shippingRates = getShippingRates().as(ShippingRatesResponse.class).getShipping_rates();

        Assert.assertNotEquals(
                shippingRates.size(),
                0,
                "Нет слотов в магазине\n" + Pages.Admin.stores(currentSid));

        DeliveryWindow deliveryWindow = shippingRates.get(0).getDelivery_window();

        currentDeliveryWindowId = deliveryWindow.getId();

        System.out.println(deliveryWindow);
    }

    /**
     * Получаем первый доступный способ доставки
     */
    private void getAvailableShippingMethod() {
        List<ShippingMethod> shippingMethods = getShippingMethod(currentSid).as(ShippingMethodsResponse.class).getShipping_methods();

        Assert.assertNotEquals(
                shippingMethods.size(),
                0,
                "Нет способов доставки в магазине\n" + Pages.Admin.stores(currentSid));

        ShippingMethod shippingMethod = shippingMethods.get(0);

        currentShipmentMethodId = shippingMethod.getId();

        System.out.println(shippingMethod);
    }

    /**
     * Выбираем id способа оплаты (по умолчанию Картой курьеру)
     */
    private void getAvailablePaymentTool() {
        List<PaymentTool> paymentTools = getPaymentTools().as(PaymentToolsResponse.class).getPayment_tools();

        StringJoiner availablePaymentTools = new StringJoiner(
                "\n• ",
                "Список доступных способов оплаты:\n• ",
                "\n");
        boolean cardCourier = false;
        for (int i = 0; i < paymentTools.size(); i++) {
            String selectedPaymentTool = greenText(paymentTools.get(i) + " <<< Выбран");
            if (paymentTools.get(i).getName().equalsIgnoreCase("Картой курьеру")) {
                currentPaymentToolId = paymentTools.get(i).getId();
                availablePaymentTools.add(selectedPaymentTool);
                cardCourier = true;
            } else if (i == paymentTools.size() - 1 && !cardCourier) { // выбираем последний способ, если нет картой курьеру
                currentPaymentToolId = paymentTools.get(i).getId();
                availablePaymentTools.add(selectedPaymentTool);
            } else availablePaymentTools.add(paymentTools.get(i).toString());
        }
        System.out.println(availablePaymentTools);
    }

    /**
     * Применяем дефолтные параметры к заказу
     */
    private void setDefaultOrderAttributes() {
        Response response = putOrders(
                currentAddressId,
                1,
                "+7 (987) 654 32 10",
                "test",
                currentPaymentToolId,
                currentShipmentId,
                currentDeliveryWindowId,
                currentShipmentMethodId);
        Order order = response.as(OrdersResponse.class).getOrder();
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
    private void completeOrder() {
        Response response = postOrdersCompletion();

        String notAvailableDeliveryWindow = "Выбранный интервал стал недоступен";
        if (response.getStatusCode() == 422)
            if (response.as(ErrorResponse.class)
                    .getErrors()
                    .getShipments()
                    .contains(notAvailableDeliveryWindow)) {
                printError(notAvailableDeliveryWindow);
                System.out.println();
                getAvailableDeliveryWindow();
                setDefaultOrderAttributes();
                response = postOrdersCompletion();
            }
        printSuccess("Оформлен заказ: " + response.as(OrdersResponse.class).getOrder().getNumber() + "\n");
    }

    /**
     * Отменяем заказ по номеру
     */
    private void cancelOrder(String number) {
        printSuccess("Отменен заказ: " + postOrdersCancellations(number)
                .as(CancellationsResponse.class)
                .getCancellation()
                .getOrder()
                .getNumber() + "\n");
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

        currentSid = store.getId();

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

                currentSid = store.getId();

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
        currentSid = sid;
        Response response = getStores(sid);

        if (response.statusCode() == 422)
            if (response.as(ErrorResponse.class)
                    .getErrors()
                    .getBase()
                    .contains("По указанному адресу"))
                Assert.fail(sid + " sid отключен");

        Store store = response.as(StoreResponse.class).getStore();

        Address address = store.getLocation();
        System.out.println("Получен адрес " + address.getFull_address() + "\n");

        List<List<Zone>> zones = store.getZones();
        address.setCoordinates(getInnerPoint(zones.get(zones.size() - 1)));

        return address;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public static List<Retailer> availableRetailers() {
        List<Retailer> retailers = getRetailers().as(RetailersResponse.class).getRetailers();

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
    public static List<Retailer> availableRetailersV1() {
        List<Retailer> retailers = getRetailersV1().as(RetailersResponse.class).getRetailers();

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
     * Получить список активных магазинов как список объектов Store
     */
    public static List<Store> availableStores() {
        List<Store> stores = getStores().as(StoresResponse.class).getStores();

        printAvailableStores(stores);

        return stores;
    }

    /**
     * Получить по координатам список активных магазинов как список объектов Store
     */
    private List<Store> availableStores(double lat, double lon) {
        List<Store> stores = getStores(lat, lon).as(StoresResponse.class).getStores();

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
     * Авторизация
     */
    private void authorisation(String email, String password) {
        token = postSessions(email, password)
                .as(SessionsResponse.class)
                .getSession()
                .getAccess_token();
        System.out.println("Авторизуемся: " + email);
        System.out.println("access_token: " + token + "\n");
    }

    /**
     * Авторизация с данными из объекта юзера
     */
    private void authorisation(UserData user) {
        authorisation(user.getLogin(), user.getPassword());
    }

    /**
     * Узнаем номер заказа
     */
    private void getCurrentOrderNumber() {
        currentOrderNumber = postOrder()
                .as(OrdersResponse.class)
                .getOrder()
                .getNumber();
        System.out.println("Номер текущего заказа: " + currentOrderNumber + "\n");
    }

    /*
      МЕТОДЫ ИЗ НЕСКОЛЬКИХ ЗАПРОСОВ
     */

    /**
     * Наполняем корзину до минимальной суммы заказа в конкретном магазине
     */
    private void fillCartOnSid(int sid) {
        List<Product> products = getProductsFromEachDepartmentInStore(
                sid,
                1,
                true);
        addItemToCart(products.get(0).getId(),1);
        getMinSum();

        int quantity = 1;
        for (Product product : products) {
            if (minSumNotReached) quantity = (int) Math.ceil(minSum / (product.getPrice() * product.getItems_per_pack()));

            double cartWeight = roundBigDecimal(getProductWeight(product) * quantity,3);

            String cartWeightText = "Вес корзины: " + cartWeight + " кг.\n";
            String anotherProductText = "Выбираем другой товар\n";
            if (cartWeight > 40) printError(cartWeightText + anotherProductText);
            else if (productWeightNotDefined) printError(anotherProductText);
            else {
                printSuccess(cartWeightText);
                if (minSumNotReached) addItemToCart(product.getId(), quantity);
                break;
            }
        }
    }

    /**
     * Применяем атрибуты заказа (способ оплаты и слот) и завершаем его
     */
    private void setDefaultAttributesAndCompleteOrder() {
        getAvailablePaymentTool();
        getAvailableShippingMethod();
        getAvailableDeliveryWindow();

        setDefaultOrderAttributes();
        completeOrder();
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
        String registeredEmail = postUsers(email, firstName, lastName, password)
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
        fillCartOnSid(currentSid);
    }

    /**
     * Наполнить корзину и выбрать адрес у юзера по определенному адресу у определенного ритейлера
     */
    public void fillCart(UserData user, Address address, String retailer) {
        dropCart(user, address);

        getCurrentSid(address, retailer);
        fillCartOnSid(currentSid);
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
    public String order(UserData user, Address address, String retailer) {
        fillCart(user, address, retailer);
        setDefaultAttributesAndCompleteOrder();
        return currentOrderNumber;
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине
     */
    public String order(UserData user, int sid) {
        fillCart(user, sid);
        setDefaultAttributesAndCompleteOrder();
        return currentOrderNumber;
    }

    /**
     * Оформить тестовый заказ у юзера в определенном магазине по определенным координатам
     */
    public String order(UserData user, int sid, Zone coordinates) {
        fillCart(user, sid, coordinates);
        setDefaultAttributesAndCompleteOrder();
        return currentOrderNumber;
    }

    /**
     * Отменить последний заказ (с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelCurrentOrder() {
        if (currentOrderNumber != null) cancelOrder(currentOrderNumber);
    }
}
