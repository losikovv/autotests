package instamart.api.shopper;

import instamart.api.common.RestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

/**
 * МЕТОДЫ ЗАПРОСОВ REST API SHOPPER
 */
public class ShopperApiRequests {
    static String token;

    /**
     * Добавляем спеки к запросу
     */
    private static RequestSpecification givenWithSpec() {
        return given().spec(RestBase.shopperRequestSpec);
    }

    /**
     * Добавляем хедер авторизации к запросу
     */
    private static RequestSpecification givenWithAuth() {
        return givenWithSpec().header(
                "Authorization",
                "Token " + token);
    }

    /**
     * Авторизация
     */
    public static Response postSessions(String email, String password) {
        return givenWithSpec()
                .auth()
                .preemptive()
                .basic(email, password)
                .post(ShopperApiEndpoints.sessions);
    }

    /**
     * Получаем инфу о шоппере
     */
    public static Response getShopper() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.shopper);
    }

    /**
     * Получаем список заказов для сборщика
     */
    public static Response getShopperShipments() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shoppers.shipments);
    }

    /**
     * Получаем список доставок сборщика
     */
    public static Response getShopperAssemblies() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shoppers.assemblies);
    }

    /**
     * Получаем список смен сборщика
     */
    public static Response getShopperOperationShifts() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shoppers.operation_shifts);
    }

    /**
     * Создаем доставку
     */
    public static Response postAssembly(String shipmentId) {
        return givenWithAuth()
                .formParam("shipment_id", shipmentId)
                .log().params()
                .post(ShopperApiEndpoints.assemblies);
    }

    /**
     * Получаем заказ
     */
    public static Response getShipment(String shipmentId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shipments.id, shipmentId);
    }

    /**
     * Получаем доставку
     */
    public static Response getAssembly(String assemblyId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Assemblies.id, assemblyId);
    }

    /**
     * Удаляем доставку
     */
    public static Response deleteAssembly(String assemblyId) {
        return givenWithAuth()
                .delete(ShopperApiEndpoints.Assemblies.id, assemblyId);
    }

    /**
     * Ищем товары
     */
    public static Response getStoreOffers(int shopperSid, String query) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Stores.offers, shopperSid, query);
    }

    /**
     * Собираем товар
     */
    public static Response patchAssemblyItem(String assemblyId, String itemId, int itemQty) {
        JSONObject requestParams = new JSONObject();
        JSONObject assemblyItem = new JSONObject();
        requestParams.put("assembly_item", assemblyItem);
        assemblyItem.put("found_qty", itemQty);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .patch(ShopperApiEndpoints.Assemblies.items, assemblyId, itemId);
    }

    /**
     * Получаем тикеты хелпдеска
     */
    public static Response getHelpdeskTickets(String shipmentId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Helpdesk.tickets, shipmentId);
    }

    /**
     * Получаем маршруты
     */
    public static Response getRoutes() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.routes);
    }
}
