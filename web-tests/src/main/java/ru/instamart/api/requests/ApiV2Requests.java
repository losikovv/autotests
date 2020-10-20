package instamart.api.requests;

import instamart.api.common.RestBase;
import instamart.api.endpoints.ApiV2EndPoints;
import instamart.api.objects.v2.Address;
import instamart.core.testdata.AuthProviders;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import javax.net.ssl.SSLHandshakeException;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * МЕТОДЫ ЗАПРОСОВ REST API V2
 */
public class ApiV2Requests {
    public static ThreadLocal<String> token = new ThreadLocal<>();
    public static ThreadLocal<String> currentOrderNumber = new ThreadLocal<>();
    public static ThreadLocal<String> currentShipmentNumber = new ThreadLocal<>();

    public static boolean authorized() {
        return token.get() != null;
    }

    /**
     * Обходим тормоза интернета + Добавляем спеки к запросу
     */
    private static RequestSpecification givenExceptions()
            throws SSLHandshakeException, SocketException, IllegalStateException {
        return given().spec(RestBase.customerRequestSpec);
    }

    /**
     * Обходим тормоза интернета + Добавляем спеки к запросу
     */
    private static RequestSpecification givenCatch() {
        for (int i = 0; i < 10; i++) {
            try {
                try {
                    try {
                        return givenExceptions();
                    } catch (SocketException socketException) {
                        System.out.println(socketException);
                    }
                } catch (SSLHandshakeException sslHandshakeException) {
                    System.out.println(sslHandshakeException);
                }
            } catch (IllegalStateException illegalStateException) {
                System.out.println(illegalStateException);
            }
        }
        return given().spec(RestBase.customerRequestSpec);
    }

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

        return givenCatch()
                .log()
                .params()
                .formParams(data)
                .post(ApiV2EndPoints.users);
    }

    /**
     * Авторизация
     */
    public static Response postSessions(String email, String password) {
        return givenCatch()
                .auth()
                .preemptive()
                .basic(email, password)
                .post(ApiV2EndPoints.sessions);
    }

    /**
     * Авторизация через стороннего провайдера
     */
    public static Response postAuthProvidersSessions(String authProviderId) {
        JSONObject sessionParams = new JSONObject();
        JSONObject requestParams = new JSONObject();
        requestParams.put("session", sessionParams);
        switch (authProviderId) {
            case AuthProviders.Metro.ID:
                sessionParams.put("uid", AuthProviders.Metro.SESSION_UID);
                sessionParams.put("digest", AuthProviders.Metro.SESSION_DIGEST);
                return givenCatch()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .log().body()
                        .post(ApiV2EndPoints.AuthProviders.sessions, authProviderId);
            case AuthProviders.SberApp.ID:
                sessionParams.put("uid", AuthProviders.SberApp.SESSION_UID);
                sessionParams.put("digest", AuthProviders.SberApp.SESSION_DIGEST);
                return givenCatch()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .log().body()
                        .post(ApiV2EndPoints.AuthProviders.sessions, authProviderId);
            case AuthProviders.Vkontakte.ID:
                return givenCatch()
                        .param("session[uid]", AuthProviders.Vkontakte.SESSION_UID)
                        .log().params()
                        .post(ApiV2EndPoints.AuthProviders.sessions, authProviderId);
            case AuthProviders.Facebook.ID:
                return givenCatch()
                        .param("session[uid]", AuthProviders.Facebook.SESSION_UID)
                        .log().params()
                        .post(ApiV2EndPoints.AuthProviders.sessions, authProviderId);
            default:
                throw new IllegalStateException("Unexpected value: " + authProviderId);
        }
    }

    /**
     * Получаем активные (принят, собирается, в пути) заказы
     */
    public static Response getActiveOrders() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.Orders.statusActive, "");
    }

    /**
     * Получаем активные (принят, собирается, в пути) заказы с указанием страницы
     */
    public static Response getActiveOrders(int page) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.Orders.statusActive, page);
    }

    /**
     * Получение заказов
     */
    public static Response getOrders() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.orders);
    }

    /**
     * Получение заказа по номеру
     */
    public static Response getOrder(String number) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.Orders.number, number);
    }

    /**
     * Создание заказа (если еще не создан)
     */
    public static Response postOrder() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(ApiV2EndPoints.orders);
    }

    /**
     * Удаление всех шипментов у заказа
     */
    public static Response deleteShipments() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .delete(ApiV2EndPoints.Orders.shipments, currentOrderNumber.get());
    }

    /**
     * Изменение/применение параметров адреса
     */
    public static Response putShipAddressChange(Address address) {
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

        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .log()
                .params()
                .formParams(data)
                .put(ApiV2EndPoints.Orders.ship_address_change, currentOrderNumber.get());
    }

    /**
     * Получение продуктов в выбранном магазине
     */
    public static Response getDepartments(int sid, int numberOfProductsFromEachDepartment) {
        return givenCatch().get(ApiV2EndPoints.departments, sid, numberOfProductsFromEachDepartment);
    }

    /**
     * Получение таксонов в выбранном магазине
     */
    public static Response getTaxons(int sid) {
        return givenCatch().get(ApiV2EndPoints.Taxons.sid, sid);
    }

    /**
     * Получение конкретного таксона в выбранном магазине
     */
    public static Response getTaxons(int taxonId, int sid) {
        return givenCatch().get(ApiV2EndPoints.Taxons.id, taxonId, sid);
    }

    /**
     * Получить продукты
     */
    public static Response getProducts(int sid, String query) {
        return givenCatch().get(ApiV2EndPoints.Products.sid, sid, query);
    }

    /**
     * Получить инфо о продукте
     */
    public static Response getProducts(long productId) {
        return givenCatch().get(ApiV2EndPoints.Products.id, productId);
    }

    /**
     * Получение поисковых подсказок
     */
    public static Response getSearchSuggestions(int sid, String query) {
        return givenCatch().get(ApiV2EndPoints.search_suggestions, sid, query);
    }

    /**
     * Добавляем товар в корзину
     */
    public static Response postLineItems(long productId, int quantity) {
        Map<String, Object> data = new HashMap<>();
        data.put("line_item[order_number]", currentOrderNumber.get());
        data.put("line_item[product_id]", productId);
        data.put("line_item[quantity]", quantity);

        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .log()
                .params()
                .formParams(data)
                .post(ApiV2EndPoints.line_items);
    }

    /**
     * Удаляем товар из корзины
     */
    public static Response deleteLineItems(long productId) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .delete(ApiV2EndPoints.LineItems.id, productId);
    }

    /**
     * Получаем товары в заказе
     */
    public static Response getOrderLineItems(String orderNumber) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.Orders.line_items, orderNumber);
    }

    /**
     * Получить информацию о текущем заказе
     */
    public static Response getOrdersCurrent() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.Orders.current);
    }

    /**
     * Получаем заказы для оценки
     */
    public static Response getUnratedOrders() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.Orders.unrated);
    }

    /**
     * Получаем доступные слоты
     */
    public static Response getShippingRates() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.Shipments.shipping_rates, currentShipmentNumber.get());
    }

    /**
     * Получаем доступные способы доставки
     */
    public static Response getShippingMethod(int sid) {
        return givenCatch().get(ApiV2EndPoints.shipping_methods, sid);
    }

    /**
     * Применяем необходимые параметры к заказу
     */
    public static Response putOrders(//int addressId, //параметр ломает оформление заказа в некоторых магазинах
                                     int replacementPolicyId, String phoneNumber, String instructions,
                                     int paymentToolId, int shipmentId, int deliveryWindowId, int shipmentMethodId) {
        Map<String, Object> data = new HashMap<>();
        //data.put("order[address_attributes][id]", addressId);
        data.put("order[replacement_policy_id]", replacementPolicyId);
        data.put("order[phone_attributes][value]", phoneNumber);
        data.put("order[address_attributes][instructions]", instructions);
        data.put("order[payment_attributes][payment_tool_id]", paymentToolId);
        data.put("order[shipments_attributes][][id]", shipmentId);
        data.put("order[shipments_attributes][][delivery_window_id]", deliveryWindowId);
        data.put("order[shipments_attributes][][shipping_method_id]", shipmentMethodId);

        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .log()
                .params()
                .formParams(data)
                .put(ApiV2EndPoints.Orders.number, currentOrderNumber.get());
    }

    /**
     * Получаем список всех доступных ритейлеров
     */
    public static Response getRetailers() {
        return givenCatch().get(ApiV2EndPoints.retailers);
    }

    /**
     * Получаем список всех доступных магазинов у ритейлера (spree api)
     */
    public static Response getRetailerStoresSpree(int retailerId) {
        return givenCatch().get(ApiV2EndPoints.Retailers.stores, retailerId);
    }

    /**
     * Получаем список всех доступных ритейлеров (spree api)
     */
    public static Response getRetailersSpree() {
        return givenCatch().get(ApiV2EndPoints.retailers_v1);
    }

    /**
     * Получаем список доступных магазинов по координатам
     */
    public static Response getStores(double lat, double lon) {
        return givenCatch().get(ApiV2EndPoints.Stores.coordinates, lat, lon);
    }

    /**
     * Получаем данные о конкретном магазине
     */
    public static Response getStores(int sid) {
        return givenCatch().get(ApiV2EndPoints.Stores.sid, sid);
    }

    /**
     * Получаем промоакции в магазине
     */
    public static Response getStorePromotionCards(int sid) {
        return givenCatch().get(ApiV2EndPoints.Stores.promotion_cards, sid);
    }

    /**
     * Получаем любимые товары
     */
    public static Response getFavoritesListItems(int sid) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(ApiV2EndPoints.favorites_list, sid);
    }

    /**
     * Получаем экраны онбординга
     */
    public static Response getOnboardingPages() {
        return givenCatch().get(ApiV2EndPoints.onboarding_pages);
    }

    /**
     * Получаем инфу о реферальной программе
     */
    public static Response getReferralProgram() {
        return givenCatch().get(ApiV2EndPoints.Promotions.referral_program);
    }

    /**
     * Получаем список способов оплаты для юзера
     */
    public static Response getPaymentTools() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .header("Client-Id",
                        "InstamartApp")
                .header("Client-Ver",
                        "5.0")
                .get(ApiV2EndPoints.payment_tools);
    }

    /**
     * Завершаем оформление заказа
     */
    public static Response postOrdersCompletion() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(ApiV2EndPoints.Orders.completion, currentOrderNumber.get());
    }

    /**
     * Отменяем заказ по номеру
     */
    public static Response postOrdersCancellations(String number) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(ApiV2EndPoints.Orders.cancellations, number);
    }
}
