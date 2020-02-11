package ru.instamart.application.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.Assert;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.objects.*;
import ru.instamart.application.rest.objects.responses.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class RestHelper extends Requests {
    private int currentSid;
    private int currentAddressId;
    private int currentShipmentId;
    private int currentDeliveryWindowId;
    private int currentPaymentToolId;
    private int currentShipmentMethodId;
    private int minSum = 0;

    public RestHelper(EnvironmentData environment) {
        String fullBaseUrl = environment.getBasicUrlWithHttpAuth();
        baseURI = fullBaseUrl.substring(0, fullBaseUrl.length() - 1);
        basePath = "api/";
        port = 443;
        config = config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .addFilter(new ErrorLoggingFilter())
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
     * Высчитываем центроид зоны доставки
     */
    private Zone getCentroid(List<Zone> zones) {
        double sumLat = 0;
        double sumLon = 0;

        for (Zone zone : zones) {
            sumLat += zone.getLat();
            sumLon += zone.getLon();
        }
        Zone newZone = new Zone();

        newZone.setLat(roundBigDecimal(sumLat / zones.size(),6));
        newZone.setLon(roundBigDecimal(sumLon / zones.size(),6));

        System.out.println("Получены координаты: lat " + newZone.getLat() + ", lon " + newZone.getLon() + "\n");

        return newZone;
    }

    /**
     * Высчитываем центроид зоны доставки (2й способ)
     */
    private Zone getCentroid2(List<Zone> zones) {
        int lastZoneIndex = zones.size() - 1;
        double lat = 0;
        double lon = 0;
        double area = 0;
        double tmp;
        int k;

        for (int i = 0; i <= lastZoneIndex; i++) {
            k = (i + 1) % (lastZoneIndex + 1);
            tmp = zones.get(i).getLat() * zones.get(k).getLon() - zones.get(k).getLat() * zones.get(i).getLon();
            area += tmp;
            lat += (zones.get(i).getLat() + zones.get(k).getLat()) * tmp;
            lon += (zones.get(i).getLon() + zones.get(k).getLon()) * tmp;
        }
        area *= 0.5;
        lat *= 1 / (6 * area);
        lon *= 1 / (6 * area);

        Zone newZone = new Zone();

        newZone.setLat(roundBigDecimal(lat,6));
        newZone.setLon(roundBigDecimal(lon,6));

        System.out.println("Получены координаты: lat " + newZone.getLat() + ", lon " + newZone.getLon() + "\n");

        return newZone;
    }

    /**
     * Получить последнюю зону доставки магазина
     */
    private List<Zone> getLastZones(Store store) {
        return store.getZones().get(store.getZones().size() - 1);
    }

    /**
     * Зеленый текст
     */
    private void printSuccess(String success) {
        System.out.print("\u001b[32m");
        System.out.println(success);
        System.out.print("\u001B[0m");
    }

    /**
     * Красный текст
     */
    private void printError(String error) {
        System.out.print("\033[0;91m");
        System.out.println(error);
        System.out.print("\u001B[0m");
    }

    /*
      МЕТОДЫ ОБРАБОТКИ ОТВЕТОВ REST API (ПРИВАТНЫЕ)
     */

    /**
     * Удаляем товары из корзины
     */
    private void deleteItemsFromCart() {
        Response response = deleteShipments();

        String order = response.as(OrdersResponse.class).getOrder().getNumber();

        printSuccess("Удалены все доставки у заказа: " + order + "\n");
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
        Response response = putShipAddressChange(address);

        Address addressFromResponse = response
                .as(ShipAddressChangeResponse.class)
                .getShip_address_change()
                .getOrder()
                .getAddress();
        currentAddressId = addressFromResponse.getId();

        printSuccess("Применен адрес: " + addressFromResponse.getFull_address());
        printSuccess("lat: " + addressFromResponse.getLat());
        printSuccess("lon: " + addressFromResponse.getLon());
        System.out.println("first_name: " + addressFromResponse.getFirst_name());
        System.out.println(" last_name: " + addressFromResponse.getLast_name());
        System.out.println("        id: " + addressFromResponse.getId());
        System.out.println("door_phone: " + addressFromResponse.getDoor_phone());
        System.out.println(" apartment: " + addressFromResponse.getApartment());
        System.out.println("  comments: " + addressFromResponse.getComments());
        System.out.println("     floor: " + addressFromResponse.getFloor());
        System.out.println("  entrance: " + addressFromResponse.getEntrance());
        System.out.println("     block: " + addressFromResponse.getBlock() + "\n");
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
     * @param numberOfLineItemsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    public static List<Product> getProductsFromEachDepartmentInStore(
            int sid, int numberOfLineItemsFromEachDepartment, boolean catchExceptions) {

        List<Department> departments = getDepartments(sid, numberOfLineItemsFromEachDepartment).as(DepartmentsResponse.class).getDepartments();

        Assert.assertNotEquals(
                departments.size(),
                0,
                "Не импортированы товары для магазина " + sid + "\n");

        List<Product> products = new ArrayList<>();

        for (Department department : departments)
            for (int i = 0; i < numberOfLineItemsFromEachDepartment; i++)
                try {
                    Product product = department.getProducts().get(i);
                    products.add(product);

                    System.out.println("Получена информация о товаре: " + product.getName());
                    System.out.println("department: " + department.getName());
                    System.out.println("        id: " + product.getId());
                    System.out.println("     count: " + product.getItems_per_pack());
                    System.out.println("    volume: " + product.getHuman_volume());
                    System.out.println("     price: " + product.getPrice() + "\n");

                } catch (NullPointerException e) {
                    String string = "Показывается пустая категория "
                            + department.getName() + " у сида " + sid + "\n";

                    if (catchExceptions) System.out.println(string);
                    else throw new NullPointerException(string);
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
        Response response = postLineItems(productId, quantity);

        LineItem lineItem = response.as(LineItemResponse.class).getLine_item();
        Product product = lineItem.getProduct();

        printSuccess("В корзину добавлен товар: " + product.getName());
        System.out.println("         id: " + product.getId());
        System.out.println("     volume: " + product.getHuman_volume());
        System.out.println("   quantity: " + lineItem.getQuantity());
        System.out.println("total_price: " + lineItem.getTotal() + "\n");
    }

    /**
     * Получаем id и номер шипмента
     * Получаем минимальную сумму заказа, если сумма не набрана
     */
    private void getMinSum() {
        Response response = getOrdersCurrent();

        Shipment shipment = response.as(OrdersResponse.class).getOrder().getShipments().get(0);

        if (shipment.getAlerts().size() > 0) {
            String alertMessage = shipment.getAlerts().get(0).getMessage().replaceAll("[^0-9]","");
            minSum = Integer.parseInt(alertMessage.substring(0, alertMessage.length()-2));
            printSuccess("Минимальная сумма корзины: " + minSum);
        } else {
            printSuccess("Минимальная сумма корзины набрана");
        }

        currentShipmentId = shipment.getId();
        currentShipmentNumber = shipment.getNumber();

        System.out.println("Номер доставки: " + currentShipmentNumber + "\n");
    }

    /**
     * Узнаем вес продукта, полученного через GET v2/departments
     */
    private double getProductWeight(Product product) {
        double itemWeight = 0;
        String volume = product.getHuman_volume();

        if (volume.contains(" кг.") || volume.contains(" л.")) {
            itemWeight = Double.parseDouble(volume.split(" ")[0]);
        } else if (volume.contains(" г.")) {
            itemWeight = Double.parseDouble(volume.split(" ")[0]) / 1000;
        } else if (volume.contains(" шт.")) {
            List<Property> properties = getProducts(product.getId())
                    .as(ProductResponse.class)
                    .getProduct()
                    .getProperties();

            int i = 0;
            while (!properties.get(i).getName().equalsIgnoreCase("вес")) i++;

            itemWeight = Double.parseDouble((
                    properties.get(i).getValue()
                            .split(" ")[0])
                    .replace(",","."));

            System.out.println("Узнаём вес продукта: " + itemWeight + " кг.\n");
        }
        return itemWeight;
    }

    /**
     * Получаем первый доступный слот
     */
    private void getAvailableDeliveryWindow() {
        List<ShippingRate> shippingRates = getShippingRates().as(ShippingRatesResponse.class).getShipping_rates();

        Assert.assertNotEquals(
                shippingRates.size(),
                0,
                "Нет слотов в выбранном магазине");

        DeliveryWindow deliveryWindow = shippingRates.get(0).getDelivery_window();

        currentDeliveryWindowId = deliveryWindow.getId();
        String starts = deliveryWindow.getStarts_at();
        String ends = deliveryWindow.getEnds_at();

        System.out.println("Получена информация о слоте:");
        System.out.println("Начинается: " + starts);
        System.out.println(" Кончается: " + ends + "\n");
    }

    /**
     * Получаем первый доступный способ доставки
     */
    private void getAvailableShippingMethod() {
        List<ShippingMethod> shippingMethods = getShippingMethod(currentSid).as(ShippingMethodsResponse.class).getShipping_methods();

        Assert.assertNotEquals(
                shippingMethods.size(),
                0,
                "Нет способов доставки в выбранном магазине");

        ShippingMethod shippingMethod = shippingMethods.get(0);

        currentShipmentMethodId = shippingMethod.getId();

        System.out.println("Получена информация о способе доставки " + shippingMethod.getName());
        System.out.println("id:  " + shippingMethod.getId() + "\n");
    }


    /**
     * Выбираем id способа оплаты (по умолчанию Картой курьеру)
     */
    private void getAvailablePaymentTool() {
        Response response = getPaymentTools();

        List<PaymentTool> paymentTools = response.as(PaymentToolsResponse.class).getPayment_tools();

        System.out.println("Список доступных способов оплаты:");
        for (PaymentTool paymentTool : paymentTools) {
            System.out.println("- " + paymentTool.getName() + ", id: " + paymentTool.getId());
        }

        int i = 0;
        try {
            while (!paymentTools.get(i).getName().equalsIgnoreCase("Картой курьеру")) {
                i++;
            }
        } catch (IndexOutOfBoundsException e) {
            i = paymentTools.size() - 1; // выбираем последний способ, если нет картой курьеру
        }
        PaymentTool selectedPaymentTool = paymentTools.get(i);

        currentPaymentToolId = selectedPaymentTool.getId();

        printSuccess("Выбран способ оплаты: " + selectedPaymentTool.getName() + ", id: " + selectedPaymentTool.getId() + "\n");
    }

    /**
     * Применяем дефолтные параметры к заказу
     */
    private void setDefaultOrderAttributes() {
        Response response = putOrders(
                currentAddressId,
                1,
                "+7 (111) 111 11 11",
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

        String string = "Выбранный интервал стал недоступен";
        if (response.getStatusCode() == 422) {
            if (response.as(ErrorResponse.class).getErrors().getShipments().contains(string)) {
                printError(string);
                System.out.println();
                getAvailableDeliveryWindow();
                setDefaultOrderAttributes();
                response = postOrdersCompletion();
            }
        }
        printSuccess("Оформлен заказ: " + response.as(OrdersResponse.class).getOrder().getNumber() + "\n");
    }

    /**
     * Отменяем заказ по номеру
     */
    private void cancelOrder(String number) {
        Response response = postOrdersCancellations(number);

        printSuccess("Отменен заказ: " + response
                    .as(CancellationsResponse.class).getCancellation().getOrder().getNumber() + "\n");
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

        printSuccess("Выбран магазин: " + store.getName());
        System.out.println("sid: " + store.getId() + "\n");
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

                printSuccess("Выбран магазин: " + store.getName() + ", sid: " + store.getId() + "\n");
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

        Store store = response.as(StoreResponse.class).getStore();

        Location location = store.getLocation();
        System.out.println("Получен адрес " + location.getFull_address());

        Address address = new Address(location);

        if (sid == 109) address.setCoordinates(getCentroid2(getLastZones(store))); //опора для зоны METRO, Копейск, просп. Победы
        else address.setCoordinates(getCentroid(getLastZones(store)));

        return address;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public static List<Retailer> availableRetailers() {
        List<Retailer> retailers = getRetailers().as(RetailersResponse.class).getRetailers();

        System.out.println("Список активных ретейлеров:");
        for (Retailer retailer : retailers) {
            System.out.println("- " + retailer.getName()  + ", id: " + retailer.getId());
        }
        System.out.println();

        return retailers;
    }

    /**
     * Получить список активных ритейлеров как список объектов Retailer
     */
    public static List<Retailer> availableRetailersV1() {
        List<Retailer> retailers = getRetailersV1().as(RetailersResponse.class).getRetailers();

        System.out.println("Список активных ретейлеров:");
        for (Retailer retailer : retailers)
            if (retailer.getAvailable()) System.out.println("- " + retailer.getName() + ", id: " + retailer.getId());
        System.out.println();

        System.out.println("Список неактивных ретейлеров:");
        for (Retailer retailer : retailers)
            if (!retailer.getAvailable()) System.out.println("- " + retailer.getName() + ", id: " + retailer.getId());
        System.out.println();

        return retailers;
    }

    /**
     * Получить список активных магазинов как список объектов Store
     */
    public static List<Store> availableStores() {
        List<Store> stores = getStores().as(StoresResponse.class).getStores();

        System.out.println("Список активных магазинов:");
        for (Store store : stores) {
            System.out.println("- " + store.getName() + ", sid: " + store.getId());
        }
        System.out.println("\n");

        return stores;
    }

    /**
     * Получить по координатам список активных магазинов как список объектов Store
     */
    private List<Store> availableStores(double lat, double lon) {
        List<Store> stores = getStores(lat, lon).as(StoresResponse.class).getStores();

        if (stores.size() > 0) {
            System.out.println("Список активных магазинов:");
            for (Store store : stores) {
                System.out.println("- " + store.getName() + ", sid: " + store.getId());
            }
        } else {
            printError("По выбранному адресу нет магазинов");
        }

        return stores;
    }

    /**
     * Авторизация
     */
    private void authorisation(String email, String password) {
        Response response = postSessions(email, password);

        token = response.as(SessionsResponse.class).getSession().getAccess_token();

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
        Response response = postOrder();

        currentOrderNumber = response.as(OrdersResponse.class).getOrder().getNumber();

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

        int i = 0;
        Product product = products.get(i);
        addItemToCart(product.getId(),1);
        getMinSum();

        int quantity = 1;
        double cartWeight;
        do {
            product = products.get(i++);

            if (minSum > 0) {
                double price = product.getPrice();
                int itemsPerPack = product.getItems_per_pack();
                quantity = (int) Math.ceil(minSum/(price * itemsPerPack));
            }

            cartWeight = roundBigDecimal(getProductWeight(product) * quantity,3);

            String string = "Вес корзины: " + cartWeight + " кг.";
            if (cartWeight > 40) printError(string + " Выбираем другой товар\n");
            else if (cartWeight > 0) printSuccess(string);

        } while (cartWeight > 40);

        if (minSum > 0) addItemToCart(product.getId(), quantity);
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
      МЕТОДЫ ДЛЯ ИСПОЛЬЗОВАНИЯ В GUI ТЕСТАХ (ПУБЛИЧНЫЕ)
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
        Response response = postUsers(email, firstName, lastName, password);

        String registeredEmail = response.as(UsersResponse.class).getUser().getEmail();

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
     * Отменить последний заказ (с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelCurrentOrder() {
        if (currentOrderNumber != null) cancelOrder(currentOrderNumber);
    }
}
