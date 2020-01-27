package ru.instamart.application.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import ru.instamart.application.models.UserData;
import ru.instamart.application.platform.modules.Base;
import ru.instamart.application.rest.objects.*;
import ru.instamart.application.rest.objects.responses.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;

public class RestHelper extends Requests {
    private String fullBaseUrl = Base.environment.getBasicUrlWithHttpAuth();
    private int currentSid;
    private int currentAddressId;
    private int currentShipmentId;
    private int currentDeliveryWindowId;
    private int minSum;

    public RestHelper() {
        baseURI = fullBaseUrl.substring(0, fullBaseUrl.length() - 1);
        basePath = "api/v2/";
        port = 443;
        config = config().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .build();
    }

    private boolean noErrors(Response response) {
        if (response.getStatusCode() != 200) {
            System.out.print("\033[0;91m");
            response.prettyPeek();
            System.out.print("\u001B[0m");
            return false;
        }
        return true;
    }

    /*
      МЕТОДЫ ОБРАБОТКИ ОТВЕТОВ REST API (ПРИВАТНЫЕ)
     */

    /**
     * Удаляем товары из корзины
     */
    private void deleteItemsFromCart() {
        Response response = deleteShipments();

        noErrors(response);

        String order = response.as(OrdersResponse.class).getOrder().getNumber();

        System.out.println("Удалены все доставки у заказа: " + order + "\n");
    }


    /**
     * Изменение/применение параматров адреса из объекта адреса
     */
    private void setAddressAttributes(Address address) {
        Response response = putShipAddressChange(address);

        noErrors(response);

        Address addressFromResponse = response
                .as(ShipAddressChangeResponse.class)
                .getShip_address_change()
                .getOrder()
                .getAddress();
        currentAddressId = address.getId();

        System.out.println("Применен адрес: " + addressFromResponse.getFull_address());
        System.out.println("door_phone: " + addressFromResponse.getDoor_phone());
        System.out.println("apartment: " + addressFromResponse.getApartment());
        System.out.println("comments: " + addressFromResponse.getComments());
        System.out.println("floor: " + addressFromResponse.getFloor());
        System.out.println("entrance: " + addressFromResponse.getEntrance());
        System.out.println("first_name: " + addressFromResponse.getFirst_name());
        System.out.println("last_name: " + addressFromResponse.getLast_name());
        System.out.println("block: " + addressFromResponse.getBlock() + "\n");
    }

    /**
     * Получаем продукт (1-й продукт из 1-й категории)
     */
    private Product getProduct(int sid) {
        return getProducts(sid,1,1).get(0);
    }

    /**
     * Получаем список продуктов
     * @param sid сид магазина
     * @param numberOfDepartments количество категорий, из которых берем продукты
     * @param numberOfLineItemsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    private List<Product> getProducts(int sid, int numberOfDepartments, int numberOfLineItemsFromEachDepartment) {
        Response response = getDepartments(sid, numberOfLineItemsFromEachDepartment);

        noErrors(response);

        DepartmentsResponse departmentsResponse = response.as(DepartmentsResponse.class);

        List<Product> products = new ArrayList<>();

        for (int d = 0; d < numberOfDepartments; d++) {

            for (int i = 0; i < numberOfLineItemsFromEachDepartment; i++) {

                Product product = departmentsResponse.getDepartments().get(d).getProducts().get(i);
                products.add(product);

                System.out.println("Получена информация о товаре: " + product.getName());
                System.out.println("department: " + departmentsResponse.getDepartments().get(d).getName());
                System.out.println("id: " + product.getId());
                System.out.println("count: " + product.getItems_per_pack());
                System.out.println("volume: " + product.getHuman_volume());
                System.out.println("price: " + product.getPrice() + "\n");
            }
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
    private void addItemToCart(int productId, int quantity) {
        Response response = postLineItems(productId, quantity);

        noErrors(response);

        LineItem lineItem = response.as(LineItemResponse.class).getLine_item();
        Product product = lineItem.getProduct();

        System.out.println("В корзину добавлен товар: " + product.getName());
        System.out.println("id: " + product.getId());
        System.out.println("volume: " + product.getHuman_volume());
        System.out.println("quantity: " + lineItem.getQuantity());
        System.out.println("total_price: " + lineItem.getTotal() + "\n");
    }

    /**
     * Получаем id и номер шипмента
     * Получаем минимальную сумму заказа, если сумма не набрана
     */
    private void getMinSum() {
        Response response = getOrdersCurrent();

        noErrors(response);

        Shipment shipment = response.as(OrdersResponse.class).getOrder().getShipments().get(0);

        if (shipment.getAlerts().size() > 0) {
            String alertMessage = shipment.getAlerts().get(0).getMessage().replaceAll("[^0-9]","");
            minSum = Integer.parseInt(alertMessage.substring(0, alertMessage.length()-2));
            System.out.println("Минимальная сумма корзины: " + minSum);
        } else {
            System.out.println("Минимальная сумма корзины набрана");
        }

        currentShipmentId = shipment.getId();
        currentShipmentNumber = shipment.getNumber();

        System.out.println("Номер доставки: " + currentShipmentNumber + "\n");
    }

    /**
     * Получаем первый доступный слот
     */
    private void getFirstAvailableDeliveryWindow() {
        Response response = getShippingRates();

        noErrors(response);

        DeliveryWindow deliveryWindow = response.as(ShippingRatesResponse.class).getShipping_rates().get(0).getDelivery_window();

        currentDeliveryWindowId = deliveryWindow.getId();
        String starts = deliveryWindow.getStarts_at();
        String ends = deliveryWindow.getEnds_at();

        System.out.println("Получена информация о слоте:");
        System.out.println("Начинается: " + starts);
        System.out.println("Кончается:  " + ends + "\n");
    }

    /**
     * Применяем дефолтные параметры к заказу
     */
    private void setDefaultOrderAttributes() {
        Response response = putOrders(
                currentAddressId,
                1,
                "+7 (111) 111 11 11",
                "Тестовый заказ",
                21198, // картой курьеру на проде
                currentShipmentId,
                currentDeliveryWindowId,
                2);

        noErrors(response);

        System.out.println("Применен слот для заказа: " + response.as(OrdersResponse.class).getOrder().getNumber() + "\n");
    }

    /**
     * Завершаем оформление заказа
     */
    private void completeOrder() {
        Response response = postOrdersCompletion();

        noErrors(response);

        System.out.println("Оформлен заказ: " + response.as(OrdersResponse.class).getOrder().getNumber() + "\n");
    }

    /**
     * Завершаем оформление заказа
     */
    private Response postOrdersCompletion() {
        return given()
                    .header(
                            "Authorization",
                            "Token token=" + token)
                    .post(EndPoints.Orders.completion, currentOrderNumber);
    }

    /**
     * Отменяем заказ по номеру
     */
    private void cancelOrder(String number) {
        Response response = postOrdersCancellations(number);

        if (noErrors(response)) {
            System.out.println("Отменен заказ: " + response
                    .as(CancellationsResponse.class).getCancellation().getOrder().getNumber() + "\n");
        }
    }

    /**
     * Отменяем заказ по номеру
     */
    private Response postOrdersCancellations(String number) {
        return given()
                    .header(
                            "Authorization",
                            "Token token=" + token)
                    .post(EndPoints.Orders.cancellations, number);
    }

    /**
     * Получаем первый доступный магазин (берем координаты из обьекта необходимого адреса)
     */
    private void getSid(Address address) {
        getSid(address.getLat(), address.getLon());
    }

    /**
     * Получаем первый доступный магазин по координатам
     */
    private void getSid(double lat, double lon) {
        Response response = getStores(lat, lon);

        noErrors(response);

        List<Store> stores = response.as(StoresResponse.class).getStores();

        if (stores.size() > 0) {
            Store store = stores.get(0);
            currentSid = store.getId();
            System.out.println("Выбран магазин: " + store.getName());
            System.out.println("sid: " + store.getId() + "\n");
        } else {
            System.out.print("\033[0;91m");
            System.out.println("По выбранному адресу нет магазинов\n");
            System.out.print("\u001B[0m");
        }
    }

    /**
     * Получаем список доступных магазинов по координатам
     */
    private Response getStores(double lat, double lon) {
        return given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .get(EndPoints.stores, lat, lon);
    }

    /**
     * Получаем первый доступный магазин определенного ритейлера (берем координаты из обьекта необходимого адреса)
     */
    private void getSid(Address address, String retailer) {
        getSid(address.getLat(), address.getLon(), retailer);
    }

    /**
     * Получаем первый доступный магазин определенного ритейлера по координатам
     */
    private void getSid(double lat, double lon, String retailer) {
        Response response = getStores(lat, lon);

        noErrors(response);

        List<Store> stores = response.as(StoresResponse.class).getStores();

        if (stores.size() > 0) {
            for (Store store : stores) {
                if (retailer.equalsIgnoreCase(store.getRetailer().getSlug())) {
                    currentSid = store.getId();
                    System.out.println("Выбран магазин: " + store.getName());
                    System.out.println("sid: " + store.getId() + "\n");
                    break;
                }
            }
            System.out.print("\033[0;91m");
            System.out.println("По выбранному адресу нет такого ритейлера\n");
            System.out.print("\u001B[0m");
        } else {
            System.out.print("\033[0;91m");
            System.out.println("По выбранному адресу нет магазинов\n");
            System.out.print("\u001B[0m");
        }
    }

    /*
      МЕТОДЫ ИЗ НЕСКОЛЬКИХ ЗАПРОСОВ
     */

    /**
     * Наполняем корзину до минимальной суммы заказа в конкретном магазине
     */
    private void fillCartOnSid(int sid) {
        Product product = getProduct(sid);

        addItemToCart(product.getId(),1);
        getMinSum();

        if (minSum > 0) {
            double price = product.getPrice();
            int itemsPerPack = product.getItems_per_pack();
            double quantity = minSum/(price * itemsPerPack);

            addItemToCart(product.getId(), ((int) Math.ceil(quantity)));
        }
    }

    /**
     * Авторизация
     */
    private void authorisation(String email, String password) {
        Response response = postSessions(email, password);

        noErrors(response);

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

        noErrors(response);

        currentOrderNumber = response.as(OrdersResponse.class).getOrder().getNumber();

        System.out.println("Номер текущего заказа: " + currentOrderNumber + "\n");
    }

    /*
      МЕТОДЫ ДЛЯ ИСПОЛЬЗОВАНИЯ В GUI ТЕСТАХ (ПУБЛИЧНЫЕ)
     */

    /**
     * Регистрация
     */
    public void registration(String email, String firstName, String lastName, String password) {
        Response response = postUsers(email, firstName, lastName, password);

        noErrors(response);

        String registeredEmail = response.as(UsersResponse.class).getUser().getEmail();

        System.out.println("Зарегистрирован: " + registeredEmail + "\n");
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
     * Наполнить корзину по умолчанию у юзера по определенному адресу
     */
    public void fillCart(UserData user, Address address) {
        authorisation(user);
        getCurrentOrderNumber();
        deleteItemsFromCart();

        getSid(address);
        address.setId(currentSid);
        setAddressAttributes(address);

        fillCartOnSid(currentSid);
    }

    /**
     * Наполнить корзину у юзера по определенному адресу у определенного ритейлера
     */
    public void fillCart(UserData user, Address address, String retailer) {
        authorisation(user);
        getCurrentOrderNumber();
        deleteItemsFromCart();

        getSid(address, retailer);
        address.setId(currentSid);
        setAddressAttributes(address);

        fillCartOnSid(currentSid);
    }

    /**
     * Очистить корзину у юзера по определенному адресу
     */
    public void dropCart(UserData user, Address address) {
        authorisation(user);
        getCurrentOrderNumber();
        deleteItemsFromCart();

        getSid(address);
        address.setId(currentSid);
        setAddressAttributes(address);
    }

    /**
     * Оформить тестовый заказ у юзера по определенному адресу
     */
    public String order(UserData user, Address address) {
        fillCart(user, address);

        getFirstAvailableDeliveryWindow();
        setDefaultOrderAttributes();
        completeOrder();

        return currentOrderNumber;
    }

    /**
     * Отменить последний заказ (с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelCurrentOrder() {
        if (currentOrderNumber != null) cancelOrder(currentOrderNumber);
    }
}
