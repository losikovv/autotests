package instamart.api.requests;

import instamart.api.common.RestBase;
import instamart.api.endpoints.ShopperApiEndpoints;
import instamart.api.enums.shopper.PackageSetLocation;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * МЕТОДЫ ЗАПРОСОВ REST API SHOPPER
 */
@SuppressWarnings("unchecked")
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
                .patch(ShopperApiEndpoints.Assemblies.item_by_id, assemblyId, itemId);
    }

    /**
     * Подтверждаем товар
     */
    @Step
    public static Response patchAssemblyItemApprove(String itemId) {
        return givenWithAuth()
                .patch(ShopperApiEndpoints.AssemblyItems.approve, itemId);
    }

    /**
     * Добавляем новый товар
     */
    @Step
    public static Response postAssemblyItem(String assemblyId, String offerUuid) {
        JSONObject requestParams = new JSONObject();
        JSONObject assemblyItem = new JSONObject();
        requestParams.put("assembly_item", assemblyItem);
        assemblyItem.put("offer_uuid", offerUuid);
        assemblyItem.put("found_qty", 2);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .post(ShopperApiEndpoints.Assemblies.items, assemblyId);
    }

    /**
     * Заменяем товар
     */
    @Step
    public static Response postAssemblyItemReplacement(String fromItemId, String toOfferUuid, int reasonId) {
        JSONObject requestParams = new JSONObject();
        JSONObject replacement = new JSONObject();
        requestParams.put("cancel_reason_id", reasonId);
        requestParams.put("replacement", replacement);
        replacement.put("offer_uuid", toOfferUuid);
        replacement.put("found_qty", 2);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .post(ShopperApiEndpoints.AssemblyItems.replacements, fromItemId);
    }

    /**
     * Отменяем товар
     */
    @Step
    public static Response postAssemblyItemCancellation(String itemId, int reasonId) {
        return givenWithAuth()
                .log().params()
                .formParam("cancel_reason_id", reasonId)
                .post(ShopperApiEndpoints.AssemblyItems.cancellations, itemId);
    }

    /**
     * Уточняем товар
     */
    @Step
    public static Response postAssemblyItemClarification(String itemId, int reasonId) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("reason_id", reasonId);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .post(ShopperApiEndpoints.AssemblyItems.clarifications, itemId);
    }

    /**
     * Допзамена для товара
     */
    @Step
    public static Response postReplacementAdditionalItem(String replacementId, String offerUuid) {
        Map<String, Object> data = new HashMap<>();
        data.put("assembly_item[offer_uuid]", offerUuid);
        data.put("assembly_item[found_qty]", 2);

        return givenWithAuth()
                .log().params()
                .formParams(data)
                .post(ShopperApiEndpoints.Replacements.additional_items, replacementId);
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

    @Step
    public static Response patchAssemblyApprove(String assemblyId) {
        return givenWithAuth()
                .patch(ShopperApiEndpoints.Assemblies.approve, assemblyId);
    }

    @Step
    public static Response putAssemblyStartPaymentVerification(String assemblyId) {
        return givenWithAuth()
                .put(ShopperApiEndpoints.Assemblies.start_payment_verification, assemblyId);
    }

    @Step
    public static Response putAssemblyFinishAssembling(String assemblyId) {
        return givenWithAuth()
                .put(ShopperApiEndpoints.Assemblies.finish_assembling, assemblyId);
    }

    @Step
    public static Response postAssemblyPackageSets(String assemblyId, int boxNumber) {
        JSONObject requestParams = new JSONObject();
        JSONArray packageSets = new JSONArray();
        requestParams.put("package_sets", packageSets);
        for (int i = 1; i <= boxNumber; i++) {
            packageSets.add(packageSet(PackageSetLocation.BASKET.getLocation(),1, i));
        }
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .post(ShopperApiEndpoints.Assemblies.package_sets, assemblyId);
    }

    private static JSONObject packageSet(PackageSetLocation packageSetLocation, int number) {
        return packageSet(packageSetLocation.getLocation(), number, packageSetLocation.getBoxNumber());
    }

    private static JSONObject packageSet(String location, int number, int boxNumber){
        JSONObject packageSet = new JSONObject();
        packageSet.put("location", location);
        packageSet.put("number", number);
        packageSet.put("box_number", boxNumber);
        return packageSet;
    }

    @Step
    public static Response postAssemblyPackageSets(String assemblyId, int basket, int rack, int fridge, int freezer) {
        JSONObject requestParams = new JSONObject();
        JSONArray packageSets = new JSONArray();
        requestParams.put("package_sets", packageSets);
        if (basket != 0) packageSets.add(packageSet(PackageSetLocation.BASKET, basket));
        if (rack != 0) packageSets.add(packageSet(PackageSetLocation.RACK, rack));
        if (fridge != 0) packageSets.add(packageSet(PackageSetLocation.FRIDGE, fridge));
        if (freezer != 0) packageSets.add(packageSet(PackageSetLocation.FREEZER, freezer));
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .post(ShopperApiEndpoints.Assemblies.package_sets, assemblyId);
    }

    @Step
    public static Response getAssemblyPackageSets(String assemblyId) {
        return givenWithAuth()
                .get(ShopperApiEndpoints.Assemblies.package_sets, assemblyId);
    }

    @Step
    public static Response putAssemblyPacker(String assemblyId) {
        return givenWithAuth()
                .put(ShopperApiEndpoints.Assemblies.packer, assemblyId);
    }

    @Step
    public static Response putAssemblyStartPurchasing(String assemblyId) {
        return givenWithAuth()
                .put(ShopperApiEndpoints.Assemblies.start_purchasing, assemblyId);
    }

    @Step
    public static Response postAssemblyReceipts(
            String assemblyId, String total, String fiscalSecret, String fiscalDocumentNumber,
            String fiscalChecksum, String paidAt, String transactionDetails) {
        JSONObject requestParams = new JSONObject();
        requestParams.put("total", total);
        requestParams.put("fiscal_secret", fiscalSecret);
        requestParams.put("fiscal_document_number", fiscalDocumentNumber);
        requestParams.put("fiscal_checksum", fiscalChecksum);
        requestParams.put("paid_at", paidAt);
        requestParams.put("transaction_details", transactionDetails);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .log().body()
                .post(ShopperApiEndpoints.Assemblies.receipts, assemblyId);
    }

    @Step
    public static Response patchAssemblyStartPackaging(String assemblyId) {
        return givenWithAuth()
                .patch(ShopperApiEndpoints.Assemblies.start_packaging, assemblyId);
    }

    @Step
    public static Response patchAssemblyPurchase(String assemblyId) {
        return givenWithAuth()
                .patch(ShopperApiEndpoints.Assemblies.purchase, assemblyId);
    }
}
