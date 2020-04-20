package ru.instamart.application.rest;

import io.restassured.response.Response;
import ru.instamart.application.rest.objects.Address;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * МЕТОДЫ ЗАПРОСОВ REST API
 */
public class Requests {
    ThreadLocal<String> token = new ThreadLocal<>();
    ThreadLocal<String> currentOrderNumber = new ThreadLocal<>();
    ThreadLocal<String> currentShipmentNumber = new ThreadLocal<>();

    /**
     * Регистрация
     */
    public static Response postUsers(String email, String firstName, String lastName, String password) {
        Map<String, Object> data = new HashMap<>();
        data.put("user[email]", email);
        data.put("user[first_name]", firstName);
        data.put("user[last_name]", lastName);
        data.put("user[password]", password);

        System.out.println();

        return given()
                .log()
                .params()
                .formParams(data)
                .post(EndPoints.users);
    }

    /**
     * Авторизация
     */
    public Response postSessions(String email, String password) {
        return given()
                .auth()
                .preemptive()
                .basic(email, password)
                .post(EndPoints.sessions);
    }

    /**
     * Создание заказа (если еще не создан)
     */
    public Response postOrder() {
        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(EndPoints.orders);
    }

    /**
     * Удаление всех шипментов у заказа
     */
    public Response deleteShipments() {
        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .delete(EndPoints.Orders.shipments, currentOrderNumber.get());
    }

    /**
     * Изменение/применение параметров адреса
     */
    public Response putShipAddressChange(Address address) {
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

        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .log()
                .params()
                .formParams(data)
                .put(EndPoints.Orders.ship_address_change, currentOrderNumber.get());
    }

    /**
     * Получение продуктов в выбранном магазине
     */
    static Response getDepartments(int sid, int numberOfProductsFromEachDepartment) {
        return given().get(EndPoints.departments, sid, numberOfProductsFromEachDepartment);
    }

    /**
     * Получение таксонов в выбранном магазине
     */
    static Response getTaxons(int sid) {
        return given().get(EndPoints.Taxons.sid, sid);
    }

    /**
     * Получение конкретного таксона в выбранном магазине
     */
    static Response getTaxons(int taxonId, int sid) {
        return given().get(EndPoints.Taxons.id, taxonId, sid);
    }

    /**
     * Получить инфо о продукте
     */
    public static Response getProducts(long productId) {
        return given().get(EndPoints.Products.id, productId);
    }

    /**
     * Добавляем товар в корзину
     */
    public Response postLineItems(long productId, int quantity) {
        Map<String, Object> data = new HashMap<>();
        data.put("line_item[order_number]", currentOrderNumber.get());
        data.put("line_item[product_id]", productId);
        data.put("line_item[quantity]", quantity);

        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .log()
                .params()
                .formParams(data)
                .post(EndPoints.line_items);
    }

    /**
     * Получить информацию о текущем заказе
     */
    public Response getOrdersCurrent() {
        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Orders.current);
    }

    /**
     * Получаем доступные слоты
     */
    public Response getShippingRates() {
        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Shipments.shipping_rates, currentShipmentNumber.get());
    }

    /**
     * Получаем доступные способы доставки
     */
    public Response getShippingMethod(int sid) {
        return given().get(EndPoints.shipping_methods, sid);
    }

    /**
     * Применяем необходимые параметры к заказу
     */
    public Response putOrders(int sid, int replacementPolicyId, String phoneNumber, String instructions,
                               int paymentToolId, int shipmentId, int deliveryWindowId, int shipmentMethodId) {
        Map<String, Object> data = new HashMap<>();
        data.put("order[address_attributes][id]", sid);
        data.put("order[replacement_policy_id]", replacementPolicyId);
        data.put("order[phone_attributes][value]", phoneNumber);
        data.put("order[address_attributes][instructions]", instructions);
        data.put("order[payment_attributes][payment_tool_id]", paymentToolId);
        data.put("order[shipments_attributes][][id]", shipmentId);
        data.put("order[shipments_attributes][][delivery_window_id]", deliveryWindowId);
        data.put("order[shipments_attributes][][shipping_method_id]", shipmentMethodId);

        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .log()
                .params()
                .formParams(data)
                .put(EndPoints.Orders.number, currentOrderNumber.get());
    }

    /**
     * Получаем список всех доступных ритейлеров
     */
    static Response getRetailers() {
        return given().get(EndPoints.retailers);
    }

    /**
     * Получаем список всех доступных ритейлеров (api v1)
     */
    static Response getRetailersV1() {
        return given().get(EndPoints.retailersV1);
    }

    /**
     * Получаем список всех доступных магазинов
     */
    static Response getStores() {
        return given().get(EndPoints.stores);
    }

    /**
     * Получаем список доступных магазинов по координатам
     */
    Response getStores(double lat, double lon) {
        return given().get(EndPoints.Stores.coordinates, lat, lon);
    }

    /**
     * Получаем данные о конкретном магазине
     */
    Response getStores(int sid) {
        return given().get(EndPoints.Stores.sid, sid);
    }

    /**
     * Получаем список способов оплаты для юзера
     */
    Response getPaymentTools() {
        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .header("Client-Id",
                        "InstamartApp")
                .header("Client-Ver",
                        "5.0")
                .get(EndPoints.payment_tools);
    }

    /**
     * Завершаем оформление заказа
     */
    Response postOrdersCompletion() {
        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(EndPoints.Orders.completion, currentOrderNumber.get());
    }

    /**
     * Отменяем заказ по номеру
     */
    Response postOrdersCancellations(String number) {
        return given()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(EndPoints.Orders.cancellations, number);
    }
}
