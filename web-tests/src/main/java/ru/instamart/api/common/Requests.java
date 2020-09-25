package instamart.api.common;

import instamart.api.objects.Address;
import instamart.core.common.AppManager;
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
 * МЕТОДЫ ЗАПРОСОВ REST API
 */
public class Requests {
    static ThreadLocal<String> token = new ThreadLocal<>();
    static ThreadLocal<String> currentOrderNumber = new ThreadLocal<>();
    ThreadLocal<String> currentShipmentNumber = new ThreadLocal<>();

    /**
     * Обходим тормоза интернета
     */
    private static RequestSpecification givenExceptions()
            throws SSLHandshakeException, SocketException, IllegalStateException {
        return given();
    }

    /**
     * Обходим тормоза интернета
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
        return given();
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
                .post(EndPoints.users);
    }

    /**
     * Авторизация
     */
    public Response postSessions(String email, String password) {
        return givenCatch()
                .auth()
                .preemptive()
                .basic(email, password)
                .post(EndPoints.sessions);
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
                        .post(EndPoints.AuthProviders.sessions, authProviderId);
            case AuthProviders.SberApp.ID:
                sessionParams.put("uid", AuthProviders.SberApp.SESSION_UID);
                sessionParams.put("digest", AuthProviders.SberApp.SESSION_DIGEST);
                return givenCatch()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .log().body()
                        .post(EndPoints.AuthProviders.sessions, authProviderId);
            case AuthProviders.Vkontakte.ID:
                return givenCatch()
                        .param("session[uid]", AuthProviders.Vkontakte.SESSION_UID)
                        .log().params()
                        .post(EndPoints.AuthProviders.sessions, authProviderId);
            case AuthProviders.Facebook.ID:
                return givenCatch()
                        .param("session[uid]", AuthProviders.Facebook.SESSION_UID)
                        .log().params()
                        .post(EndPoints.AuthProviders.sessions, authProviderId);
            default:
                throw new IllegalStateException("Unexpected value: " + authProviderId);
        }
    }

    /**
     * Получаем активные (принят, собирается, в пути) заказы
     */
    public Response getActiveOrders() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Orders.statusActive, "");
    }

    /**
     * Получаем активные (принят, собирается, в пути) заказы с указанием страницы
     */
    public Response getActiveOrders(int page) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Orders.statusActive, page);
    }

    /**
     * Получение заказов
     */
    public static Response getOrders() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.orders);
    }

    /**
     * Получение заказа по номеру
     */
    public static Response getOrder(String number) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Orders.number, number);
    }

    /**
     * Создание заказа (если еще не создан)
     */
    public Response postOrder() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(EndPoints.orders);
    }

    /**
     * Удаление всех шипментов у заказа
     */
    public Response deleteShipments() {
        return givenCatch()
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

        return givenCatch()
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
        return givenCatch().get(EndPoints.departments, sid, numberOfProductsFromEachDepartment);
    }

    /**
     * Получение таксонов в выбранном магазине
     */
    public static Response getTaxons(int sid) {
        return givenCatch().get(EndPoints.Taxons.sid, sid);
    }

    /**
     * Получение конкретного таксона в выбранном магазине
     */
    public static Response getTaxons(int taxonId, int sid) {
        return givenCatch().get(EndPoints.Taxons.id, taxonId, sid);
    }

    /**
     * Получить продукты
     */
    public static Response getProducts(int sid, String query) {
        return givenCatch().get(EndPoints.Products.sid, sid, query);
    }

    /**
     * Получить инфо о продукте
     */
    public static Response getProducts(long productId) {
        return givenCatch().get(EndPoints.Products.id, productId);
    }

    /**
     * Получение поисковых подсказок
     */
    public static Response getSearchSuggestions(int sid, String query) {
        return givenCatch().get(EndPoints.search_suggestions, sid, query);
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
                .post(EndPoints.line_items);
    }

    /**
     * Удаляем товар из корзины
     */
    public static Response deleteLineItems(long productId) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .delete(EndPoints.LineItems.id, productId);
    }

    /**
     * Получаем товары в заказе
     */
    public static Response getOrderLineItems(String orderNumber) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Orders.line_items, orderNumber);
    }

    /**
     * Получить информацию о текущем заказе
     */
    public static Response getOrdersCurrent() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Orders.current);
    }

    /**
     * Получаем заказы для оценки
     */
    public static Response getUnratedOrders() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Orders.unrated);
    }

    /**
     * Получаем доступные слоты
     */
    public Response getShippingRates() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.Shipments.shipping_rates, currentShipmentNumber.get());
    }

    /**
     * Получаем доступные способы доставки
     */
    public Response getShippingMethod(int sid) {
        return givenCatch().get(EndPoints.shipping_methods, sid);
    }

    /**
     * Применяем необходимые параметры к заказу
     */
    public Response putOrders(//int addressId, //параметр ломает оформление заказа в некоторых магазинах
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
                .put(EndPoints.Orders.number, currentOrderNumber.get());
    }

    /**
     * Получаем список всех доступных ритейлеров
     */
    static Response getRetailers() {
        return givenCatch().get(EndPoints.retailers);
    }

    /**
     * Получаем список всех доступных магазинов у ритейлера (spree api)
     */
    static Response getRetailerStoresSpree(int retailerId) {
        return givenCatch().get(EndPoints.Retailers.stores, retailerId);
    }

    /**
     * Получаем список всех доступных ритейлеров (spree api)
     */
    static Response getRetailersSpree() {
        return givenCatch().get(EndPoints.retailers_v1);
    }

    /**
     * Получаем список всех доступных магазинов
     */
    public static Response getStores() {
        if (AppManager.environment.getBasicUrl().equalsIgnoreCase("sbermarket.ru")) {
            return givenCatch()
                    .baseUri("https://api.sbermarket.ru")
                    .basePath("")
                    .get(EndPoints.stores);
        } else return givenCatch().get(EndPoints.stores);
    }

    /**
     * Получаем список доступных магазинов по координатам
     */
    Response getStores(double lat, double lon) {
        return givenCatch().get(EndPoints.Stores.coordinates, lat, lon);
    }

    /**
     * Получаем данные о конкретном магазине
     */
    public static Response getStores(int sid) {
        return givenCatch().get(EndPoints.Stores.sid, sid);
    }

    /**
     * Получаем промоакции в магазине
     */
    public static Response getStorePromotionCards(int sid) {
        return givenCatch().get(EndPoints.Stores.promotion_cards, sid);
    }

    /**
     * Получаем любимые товары
     */
    public static Response getFavoritesListItems(int sid) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .get(EndPoints.favorites_list, sid);
    }

    /**
     * Получаем экраны онбординга
     */
    public static Response getOnboardingPages() {
        return givenCatch().get(EndPoints.onboarding_pages);
    }

    /**
     * Получаем инфу о реферальной программе
     */
    public static Response getReferralProgram() {
        return givenCatch().get(EndPoints.Promotions.referral_program);
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
                .get(EndPoints.payment_tools);
    }

    /**
     * Завершаем оформление заказа
     */
    Response postOrdersCompletion() {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(EndPoints.Orders.completion, currentOrderNumber.get());
    }

    /**
     * Отменяем заказ по номеру
     */
    Response postOrdersCancellations(String number) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token.get())
                .post(EndPoints.Orders.cancellations, number);
    }
}
