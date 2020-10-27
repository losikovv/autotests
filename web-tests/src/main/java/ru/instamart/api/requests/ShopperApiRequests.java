package instamart.api.requests;

import instamart.api.common.RestBase;
import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;

/**
 * МЕТОДЫ ЗАПРОСОВ REST API SHOPPER
 */
public class ShopperApiRequests {
    public static String token;

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
    @Step
    public static Response postSessions(String login, String password) {
        return givenWithSpec()
                .auth()
                .preemptive()
                .basic(login, password)
                .post(ShopperApiEndpoints.sessions);
    }

    /**
     * Получаем инфу о шоппере
     */
    @Step
    public static Response getCurrentAppVersion() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.current_app_version);
    }

    /**
     * Получаем инфу о шоппере
     */
    @Step
    public static Response getShopper() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.shopper);
    }

    /**
     * Получаем список заказов для сборщика
     */
    @Step
    public static Response getShopperShipments() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shopper.shipments);
    }

    /**
     * Получаем список доставок сборщика
     */
    @Step
    public static Response getShopperAssemblies() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shopper.assemblies);
    }

    /**
     * Создаём смену для сборщика
     */
    @Step
    public static Response postShopperOperationShifts(
            int roleId, boolean started, String planStartsAt, String planEndsAt, double lat, double lon) {
        JSONObject requestParams = new JSONObject();
        JSONObject operationShift = new JSONObject();
        JSONObject location = new JSONObject();
        requestParams.put("operation_shift", operationShift);
        requestParams.put("location", location);
        operationShift.put("role_id", roleId);
        operationShift.put("started", started);
        operationShift.put("plan_starts_at", planStartsAt);
        operationShift.put("plan_ends_at", planEndsAt);
        location.put("latitude", lat);
        location.put("longitude", lon);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .post(ShopperApiEndpoints.Shopper.operation_shifts);
    }

    /**
     * Получаем список смен сборщика
     */
    @Step
    public static Response getShopperOperationShifts() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shopper.operation_shifts);
    }

    /**
     * Получаем список заказов для сборщика
     */
    @Step
    public static Response getPackerShipments() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Packer.shipments);
    }

    /**
     * Получаем список доставок сборщика
     */
    @Step
    public static Response getPackerAssemblies() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Packer.assemblies);
    }

    /**
     * Получаем список заказов для водителя
     */
    @Step
    public static Response getDriverShipments() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Driver.shipments);
    }

    /**
     * Создаем доставку
     */
    @Step
    public static Response postAssembly(String shipmentId) {
        return givenWithAuth()
                .formParam("shipment_id", shipmentId)
                .log().params()
                .post(ShopperApiEndpoints.assemblies);
    }

    /**
     * Получаем заказ
     */
    @Step
    public static Response getShipment(String shipmentId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shipments.id, shipmentId);
    }

    /**
     * Получаем стоки заказа
     */
    @Step
    public static Response getShipmentStocks(String shipmentId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Shipments.stocks, shipmentId);
    }

    /**
     * Получаем доставку
     */
    @Step
    public static Response getAssembly(String assemblyId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Assemblies.id, assemblyId);
    }

    /**
     * Удаляем доставку
     */
    @Step
    public static Response deleteAssembly(String assemblyId) {
        return givenWithAuth()
                .delete(ShopperApiEndpoints.Assemblies.id, assemblyId);
    }

    /**
     * Ищем товары
     */
    @Step
    public static Response getStoreOffers(int shopperSid, String query) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Stores.offers, shopperSid, query);
    }

    /**
     * Собираем товар
     */
    @Step
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
     * Получаем предзамены для товара
     */
    @Step
    public static Response getAssemblyItemPrereplacements(String assemblyItemId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.AssemblyItems.prereplacements, assemblyItemId);
    }

    /**
     * Получаем тикеты хелпдеска
     */
    @Step
    public static Response getHelpdeskTickets(String shipmentId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Helpdesk.tickets, shipmentId);
    }

    /**
     * Получаем маршруты
     */
    @Step
    public static Response getRoutes() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.routes);
    }

    /**
     * Получаем причины отмен
     */
    @Step
    public static Response getCancelReasons() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.cancel_reasons);
    }

    /**
     * Получаем причины несоответсвия
     */
    @Step
    public static Response getClarifyReasons() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.clarify_reasons);
    }

    /**
     * Получаем причины возврата
     */
    @Step
    public static Response getReturnReasons() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.return_reasons);
    }

    /**
     * Получаем марс токен (стоки метро)
     */
    @Step
    public static Response getMarsToken() {
        return givenWithAuth()
                .get(ShopperApiEndpoints.mars_token);
    }
}
