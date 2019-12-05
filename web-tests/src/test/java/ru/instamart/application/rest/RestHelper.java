package ru.instamart.application.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.objects.*;
import ru.instamart.application.rest.objects.responses.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static ru.instamart.application.platform.modules.Base.fullBaseUrl;

public class RestHelper {
    private String token;
    private String currentOrderNumber;
    private int currentSid;
    private int currentAddressId;
    private int currentShipmentId;
    private String currentShipmentNumber;
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
      МЕТОДЫ ЗАПРОСОВ
     */

    /**
     * Авторизация
     */
    private void postSessions(UserData user) {
        Response response = given()
                .auth()
                .preemptive()
                .basic(user.getLogin(),user.getPassword())
                .post(EndPoints.sessions);
        noErrors(response);

        token = response.as(SessionsResponse.class).getSession().getAccess_token();

        System.out.println("Авторизуемся: " + user.getLogin());
        System.out.println("access_token: " + token + "\n");
    }

    /**
     * Узнаем номер заказа
     */
    private void postOrders() {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .post(EndPoints.orders);
        noErrors(response);

        currentOrderNumber = response.as(OrdersResponse.class).getOrder().getNumber();

        System.out.println("Номер текущего заказа: " + currentOrderNumber + "\n");
    }

    /**
     * Удаляем старые шипменты
     */
    private void deleteShipments() {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .delete(EndPoints.Orders.shipments, currentOrderNumber);
        noErrors(response);

        String order = response.as(OrdersResponse.class).getOrder().getNumber();

        System.out.println("Удалены все доставки у заказа: " + order + "\n");
    }

    /**
     * Изменение/применение параматров адреса из объекта адреса
     */
    private void putShipAddressChange(Address address) {
        Map<String, Object> data = new HashMap<>();

        if (address.getCity() != null) data.put("ship_address[city]", address.getCity());
        if (address.getStreet() != null) data.put("ship_address[street]", address.getStreet());
        if (address.getBuilding() != null) data.put("ship_address[building]", address.getBuilding());
        if (address.getDoor_phone() != null) data.put("ship_address[door_phone]", address.getDoor_phone());
        if (address.getApartment() != null) data.put("ship_address[apartment]", address.getApartment());
        if (address.getComments() != null) data.put("ship_address[comments]", address.getComments());
        if (address.getFloor() != null) data.put("ship_address[floor]", address.getFloor());
        if (address.getEntrance() != null) data.put("ship_address[entrance]", address.getEntrance());
        if (address.getLat() != null) data.put("ship_address[lat]", address.getLat());
        if (address.getLon() != null) data.put("ship_address[lon]", address.getLon());
        if (address.getFirst_name() != null) data.put("ship_address[first_name]", address.getFirst_name());
        if (address.getLast_name() != null) data.put("ship_address[last_name]", address.getLast_name());
        if (address.getBlock() != null) data.put("ship_address[block]", address.getBlock());

        putShipAddressChange(data);
    }

    /**
     * Изменение/применение параматров адреса из "мапы"
     */
    private void putShipAddressChange(Map<String, Object> data) {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .log()
                .params()
                .formParams(data)
                .put(EndPoints.Orders.ship_address_change, currentOrderNumber);
        noErrors(response);

        Address address = response.as(ShipAddressChangeResponse.class).getShip_address_change().getOrder().getAddress();
        currentAddressId = address.getId();

        System.out.println("Применен адрес: " + address.getFull_address());
        System.out.println("door_phone: " + address.getDoor_phone());
        System.out.println("apartment: " + address.getApartment());
        System.out.println("comments: " + address.getComments());
        System.out.println("floor: " + address.getFloor());
        System.out.println("entrance: " + address.getEntrance());
        System.out.println("first_name: " + address.getFirst_name());
        System.out.println("last_name: " + address.getLast_name());
        System.out.println("block: " + address.getBlock() + "\n");
    }

    /**
     * Получаем список продуктов по умолчанию (1 продукт из 1 категории)
     */
    private Product getDepartments(int sid) {
        return getDepartments(sid,1,1).get(0);
    }

    /**
     * Получаем список продуктов
     * @param sid сид магазина
     * @param numberOfDepartments количество категорий, из которых берем продукты
     * @param numberOfLineItemsFromEachDepartment количество продуктов из каждой категории (не больше 6)
     */
    private List<Product> getDepartments(int sid, int numberOfDepartments, int numberOfLineItemsFromEachDepartment) {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .get(EndPoints.departments, sid, numberOfLineItemsFromEachDepartment);
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
    public void postLineItems(List<Product> products, int quantity) {
        for (Product product : products) {
            postLineItems(product.getId(), quantity);
        }
    }

    /**
     * Добавляем товар в корзину
     */
    private void postLineItems(int productId, int quantity) {
        Map<String, Object> data = new HashMap<>();
        data.put("line_item[order_number]", currentOrderNumber);
        data.put("line_item[product_id]", productId);
        data.put("line_item[quantity]", quantity);

        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .formParams(data)
                .post(EndPoints.line_items);
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
    private void getOrdersCurrent() {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .get(EndPoints.Orders.current);
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
    private void getShippingRates() {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .get(EndPoints.Shipments.shipping_rates, currentShipmentNumber);
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
     * Применяем необходимые параметры к заказу
     */
    private void putOrders() {
        Map<String, Object> data = new HashMap<>();
        data.put("order[address_attributes][id]", currentAddressId);
        data.put("order[replacement_policy_id]", 1);
        data.put("order[phone_attributes][value]", "+7 (111) 111 11 11");
        data.put("order[address_attributes][instructions]", "Тестовый заказ");
        data.put("order[payment_attributes][payment_tool_id]", 21198); // картой курьеру
        data.put("order[shipments_attributes][][id]", currentShipmentId);
        data.put("order[shipments_attributes][][delivery_window_id]", currentDeliveryWindowId);
        data.put("order[shipments_attributes][][shipping_method_id]", 2);

        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .formParams(data)
                .put(EndPoints.Orders.number, currentOrderNumber);
        noErrors(response);

        System.out.println("Применен слот для заказа: " + response.as(OrdersResponse.class).getOrder().getNumber() + "\n");
    }

    /**
     * Завершаем оформление заказа
     */
    private void postOrdersCompletion() {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .post(EndPoints.Orders.completion, currentOrderNumber);
        noErrors(response);

        System.out.println("Оформлен заказ: " + response.as(OrdersResponse.class).getOrder().getNumber() + "\n");
    }

    /**
     * Отменяем заказ по номеру
     */
    private void postOrdersCancellations(String number) {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .post(EndPoints.Orders.cancellations, number);

        if (noErrors(response)) {
            System.out.println("Отменен заказ: " + response
                    .as(CancellationsResponse.class).getCancellation().getOrder().getNumber() + "\n");
        }
    }

    /**
     * Получаем первый доступный магазин (берем координаты из обьекта необходимого адреса)
     */
    private void getStores(Address address) {
        getStores(address.getLat(), address.getLon());
    }

    /**
     * Получаем первый доступный магазин по координатам
     */
    private void getStores(double lat, double lon) {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .get(EndPoints.stores, lat, lon);
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
     * Получаем первый доступный магазин определенного ритейлера (берем координаты из обьекта необходимого адреса)
     */
    private void getStores(Address address, String retailer) {
        getStores(address.getLat(), address.getLon(), retailer);
    }

    /**
     * Получаем первый доступный магазин определенного ритейлера по координатам
     */
    private void getStores(double lat, double lon, String retailer) {
        Response response = given()
                .header(
                        "Authorization",
                        "Token token=" + token)
                .get(EndPoints.stores, lat, lon);
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
        Product product = getDepartments(sid);

        postLineItems(product.getId(),1);
        getOrdersCurrent();

        if (minSum > 0) {
            double price = product.getPrice();
            int itemsPerPack = product.getItems_per_pack();
            double quantity = minSum/(price * itemsPerPack);

            postLineItems(product.getId(), ((int) Math.ceil(quantity)));
        }
    }

    /*
      МЕТОДЫ ДЛЯ ИСПОЛЬЗОВАНИЯ В ТЕСТАХ
     */

    /**
     * Поменять адрес у юзера
     */
    public void setAddress(UserData user, Address address) {
        postSessions(user);
        postOrders();

        putShipAddressChange(address);
    }

    /**
     * Наполнить корзину по умолчанию у юзера по определенному адресу
     */
    public void fillCart(UserData user, Address address) {
        postSessions(user);
        postOrders();
        deleteShipments();

        getStores(address);
        address.setId(currentSid);
        putShipAddressChange(address);

        fillCartOnSid(currentSid);
    }

    /**
     * Наполнить корзину у юзера по определенному адресу у определенного ритейлера
     */
    public void fillCart(UserData user, Address address, String retailer) {
        postSessions(user);
        postOrders();
        deleteShipments();

        getStores(address, retailer);
        address.setId(currentSid);
        putShipAddressChange(address);

        fillCartOnSid(currentSid);
    }

    /**
     * Очистить корзину у юзера по определенному адресу
     */
    public void dropCart(UserData user, Address address) {
        postSessions(user);
        postOrders();
        deleteShipments();

        getStores(address);
        address.setId(currentSid);
        putShipAddressChange(address);
    }

    /**
     * Оформить тестовый заказ у юзера по определенному адресу
     */
    public String order(UserData user, Address address) {
        fillCart(user, address);

        getShippingRates();
        putOrders();
        postOrdersCompletion();

        return currentOrderNumber;
    }

    /**
     * Отменить последний заказ (с которым взаимодействовали в этой сессии через REST API)
     */
    public void cancelCurrentOrder() {
        if (currentOrderNumber != null) postOrdersCancellations(currentOrderNumber);
    }
}
