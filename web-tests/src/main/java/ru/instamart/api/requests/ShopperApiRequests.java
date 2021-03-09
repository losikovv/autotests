package instamart.api.requests;

import instamart.api.common.Specification;
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
    private static String accessToken;
    private static String refreshToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        ShopperApiRequests.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        ShopperApiRequests.refreshToken = refreshToken;
    }

    /**
     * Добавляем спеки к запросу
     */
    private static RequestSpecification givenWithSpec() {
        return given().spec(Specification.INSTANCE.getShopperRequestSpec());
    }

    /**
     * Добавляем хедер авторизации к запросу
     */
    private static RequestSpecification givenWithAuth() {
        return givenWithSpec().header(
                "Authorization",
                "Token " + accessToken);
    }

    public static class Sessions {
        /**
         * Авторизация
         */
        @Step("{method} /" + ShopperApiEndpoints.SESSIONS)
        public static Response POST(String login, String password) {
            return givenWithSpec()
                    .auth()
                    .preemptive()
                    .basic(login, password)
                    .post(ShopperApiEndpoints.SESSIONS);
        }
    }

    public static class Auth {
        public static class Refresh {
            /**
             * Обновление авторизации
             */
            @Step("{method} /" + ShopperApiEndpoints.Auth.REFRESH)
            public static Response POST() {
                JSONObject requestParams = new JSONObject();
                requestParams.put("refresh_token", refreshToken);
                return givenWithSpec()
                        .contentType(ContentType.JSON)
                        .body(requestParams)
                        .post(ShopperApiEndpoints.Auth.REFRESH);
            }
        }
    }

    public static class CurrentAppVersion {
        /**
         * Получаем инфу о текущей версии приложения
         */
        @Step("{method} /" + ShopperApiEndpoints.CURRENT_APP_VERSION)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.CURRENT_APP_VERSION);
        }
    }

    public static class Shopper {

        /**
         * Получаем инфу о шоппере
         */
        @Step("{method} /" + ShopperApiEndpoints.SHOPPER)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.SHOPPER);
        }

        public static class Shipments {
            /**
             * Получаем список заказов для сборщика
             */
            @Step("{method} /" + ShopperApiEndpoints.Shopper.SHIPMENTS)
            public static Response GET() {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Shopper.SHIPMENTS);
            }
        }
        public static class Assemblies {
            /**
             * Получаем список доставок сборщика
             */
            @Step("{method} /" + ShopperApiEndpoints.Shopper.ASSEMBLIES)
            public static Response GET() {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Shopper.ASSEMBLIES);
            }
        }
        public static class OperationShifts {
            /**
             * Получаем список смен сборщика
             */
            @Step("{method} /" + ShopperApiEndpoints.Shopper.OPERATION_SHIFTS)
            public static Response GET() {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Shopper.OPERATION_SHIFTS);
            }
            /**
             * Создаём смену для сборщика
             */
            @Step("{method} /" + ShopperApiEndpoints.Shopper.OPERATION_SHIFTS)
            public static Response POST(
                    int roleId,
                    boolean started,
                    String planStartsAt,
                    String planEndsAt,
                    double lat,
                    double lon) {
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
                        .post(ShopperApiEndpoints.Shopper.OPERATION_SHIFTS);
            }
        }
    }

    public static class Packer {
        public static class Shipments {
            /**
             * Получаем список заказов для сборщика
             */
            @Step("{method} /" + ShopperApiEndpoints.Packer.SHIPMENTS)
            public static Response GET() {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Packer.SHIPMENTS);
            }
        }
        public static class Assemblies {
            /**
             * Получаем список доставок сборщика
             */
            @Step("{method} /" + ShopperApiEndpoints.Packer.ASSEMBLIES)
            public static Response GET() {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Packer.ASSEMBLIES);
            }
        }
    }

    public static class Driver {
        public static class Shipments {
            /**
             * Получаем список заказов для водителя
             */
            @Step("{method} /" + ShopperApiEndpoints.Driver.SHIPMENTS)
            public static Response GET() {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Driver.SHIPMENTS);
            }
        }
    }

    public static class Assemblies {
        /**
         * Получаем доставку
         */
        @Step("{method} /" + ShopperApiEndpoints.Assemblies.ID)
        public static Response GET(String assemblyId) {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Assemblies.ID, assemblyId);
        }

        /**
         * Создаем доставку
         */
        @Step("{method} /" + ShopperApiEndpoints.ASSEMBLIES)
        public static Response POST(String shipmentId) {
            return givenWithAuth()
                    .formParam("shipment_id", shipmentId)
                    .post(ShopperApiEndpoints.ASSEMBLIES);
        }

        /**
         * Удаляем доставку
         */
        @Step("{method} /" + ShopperApiEndpoints.Assemblies.ID)
        public static Response DELETE(String assemblyId) {
            return givenWithAuth()
                    .delete(ShopperApiEndpoints.Assemblies.ID, assemblyId);
        }

        public static class Items {
            /**
             * Добавляем новый товар
             */
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.ITEMS)
            public static Response POST(String assemblyId, String offerUuid, int foundQty) {
                JSONObject requestParams = new JSONObject();
                JSONObject assemblyItem = new JSONObject();
                requestParams.put("assembly_item", assemblyItem);
                assemblyItem.put("offer_uuid", offerUuid);
                assemblyItem.put("found_qty", foundQty);
                return givenWithAuth()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .post(ShopperApiEndpoints.Assemblies.ITEMS, assemblyId);
            }
        }
        public static class Approve {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.APPROVE)
            public static Response PATCH(String assemblyId) {
                return givenWithAuth()
                        .patch(ShopperApiEndpoints.Assemblies.APPROVE, assemblyId);
            }
        }
        public static class StartPaymentVerification {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.START_PAYMENT_VERIFICATION)
            public static Response PUT(String assemblyId) {
                return givenWithAuth()
                        .put(ShopperApiEndpoints.Assemblies.START_PAYMENT_VERIFICATION, assemblyId);
            }
        }
        public static class FinishAssembling {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.FINISH_ASSEMBLING)
            public static Response PUT(String assemblyId) {
                return givenWithAuth()
                        .put(ShopperApiEndpoints.Assemblies.FINISH_ASSEMBLING, assemblyId);
            }
        }
        public static class PackageSets {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.PACKAGE_SETS)
            public static Response GET(String assemblyId) {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Assemblies.PACKAGE_SETS, assemblyId);
            }
            /**
             * Сборщик складывает товары для передачи упаковщику
             * @param boxNumber - количество ящиков
             */
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.PACKAGE_SETS)
            public static Response POST(String assemblyId, int boxNumber) {
                JSONObject requestParams = new JSONObject();
                JSONArray packageSets = new JSONArray();
                requestParams.put("package_sets", packageSets);
                for (int i = 1; i <= boxNumber; i++) {
                    packageSets.add(packageSet(PackageSetLocation.BASKET.getLocation(),1, i));
                }
                return givenWithAuth()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .post(ShopperApiEndpoints.Assemblies.PACKAGE_SETS, assemblyId);
            }

            /**
             * Упаковщик складывает товары для передачи курьеру
             * @param basket - кол-во товаров в ящике
             * @param rack - кол-во товаров на стеллаже
             * @param fridge - кол-во товаров в холодильнике
             * @param freezer - кол-во товаров в морозилке
             */
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.PACKAGE_SETS)
            public static Response POST(
                    String assemblyId,
                    int basket,
                    int rack,
                    int fridge,
                    int freezer) {
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
                        .post(ShopperApiEndpoints.Assemblies.PACKAGE_SETS, assemblyId);
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
        }
        public static class Packer {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.PACKER)
            public static Response PUT(String assemblyId) {
                return givenWithAuth()
                        .put(ShopperApiEndpoints.Assemblies.PACKER, assemblyId);
            }
        }
        public static class StartPurchasing {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.START_PURCHASING)
            public static Response PUT(String assemblyId) {
                return givenWithAuth()
                        .put(ShopperApiEndpoints.Assemblies.START_PURCHASING, assemblyId);
            }
        }
        public static class Receipts {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.RECEIPTS)
            public static Response POST(
                    String assemblyId,
                    String total,
                    String fiscalSecret,
                    String fiscalDocumentNumber,
                    String fiscalChecksum,
                    String paidAt,
                    String transactionDetails) {
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
                        .post(ShopperApiEndpoints.Assemblies.RECEIPTS, assemblyId);
            }
        }
        public static class StartPackaging {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.START_PACKAGING)
            public static Response PATCH(String assemblyId) {
                return givenWithAuth()
                        .patch(ShopperApiEndpoints.Assemblies.START_PACKAGING, assemblyId);
            }
        }
        public static class Purchase {
            @Step("{method} /" + ShopperApiEndpoints.Assemblies.PURCHASE)
            public static Response PATCH(String assemblyId) {
                return givenWithAuth()
                        .patch(ShopperApiEndpoints.Assemblies.PURCHASE, assemblyId);
            }
        }
        public static class Pause {
            //todo
        }
        public static class Suspend {
            //todo
        }
        public static class Ship {
            //todo
        }
        public static class Trolleys {
            //todo
        }
        public static class ApproveNeedReviewItems {
            //todo
        }
    }
    public static class AssemblyItems {
        /**
         * Собираем товар
         */
        @Step("{method} /" + ShopperApiEndpoints.Assemblies.Items.ID)
        public static Response PATCH(String assemblyId, String itemId, int itemQty) {
            JSONObject requestParams = new JSONObject();
            JSONObject assemblyItem = new JSONObject();
            requestParams.put("assembly_item", assemblyItem);
            assemblyItem.put("found_qty", itemQty);
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .patch(ShopperApiEndpoints.Assemblies.Items.ID, assemblyId, itemId);
        }

        public static class Approve {
            /**
             * Подтверждаем товар
             */
            @Step("{method} /" + ShopperApiEndpoints.AssemblyItems.APPROVE)
            public static Response PATCH(String itemId) {
                return givenWithAuth()
                        .patch(ShopperApiEndpoints.AssemblyItems.APPROVE, itemId);
            }
        }
        public static class Cancellations {
            /**
             * Отменяем товар
             */
            @Step("{method} /" + ShopperApiEndpoints.AssemblyItems.CANCELLATIONS)
            public static Response POST(String itemId, int reasonId) {
                return givenWithAuth()
                        .formParam("cancel_reason_id", reasonId)
                        .post(ShopperApiEndpoints.AssemblyItems.CANCELLATIONS, itemId);
            }
        }
        public static class Replacements {
            /**
             * Заменяем товар
             */
            @Step("{method} /" + ShopperApiEndpoints.AssemblyItems.REPLACEMENTS)
            public static Response POST(
                    String itemId,
                    String replacementOfferUuid,
                    int reasonId,
                    int foundQty) {
                JSONObject requestParams = new JSONObject();
                JSONObject replacement = new JSONObject();
                requestParams.put("cancel_reason_id", reasonId);
                requestParams.put("replacement", replacement);
                replacement.put("offer_uuid", replacementOfferUuid);
                replacement.put("found_qty", foundQty);
                return givenWithAuth()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .post(ShopperApiEndpoints.AssemblyItems.REPLACEMENTS, itemId);
            }
        }
        public static class Clarifications {
            /**
             * Уточняем товар
             */
            @Step("{method} /" + ShopperApiEndpoints.AssemblyItems.CLARIFICATIONS)
            public static Response POST(String itemId, int reasonId) {
                JSONObject requestParams = new JSONObject();
                requestParams.put("reason_id", reasonId);
                return givenWithAuth()
                        .body(requestParams)
                        .contentType(ContentType.JSON)
                        .post(ShopperApiEndpoints.AssemblyItems.CLARIFICATIONS, itemId);
            }
        }
        public static class Prereplacements {
            /**
             * Получаем предзамены для товара
             */
            @Step("{method} /" + ShopperApiEndpoints.AssemblyItems.PREREPLACEMENTS)
            public static Response GET(String itemId) {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.AssemblyItems.PREREPLACEMENTS, itemId);
            }
        }
    }
    public static class Replacements {
        public static class AdditionalItems {
            /**
             * Допзамена для товара
             */
            @Step("{method} /" + ShopperApiEndpoints.Replacements.ADDITIONAL_ITEMS)
            public static Response POST(String replacementId, String offerUuid, int foundQty) {
                Map<String, Object> data = new HashMap<>();
                data.put("assembly_item[offer_uuid]", offerUuid);
                data.put("assembly_item[found_qty]", foundQty);

                return givenWithAuth()
                        .formParams(data)
                        .post(ShopperApiEndpoints.Replacements.ADDITIONAL_ITEMS, replacementId);
            }
        }
    }
    public static class Shipments {
        /**
         * Получаем заказ
         */
        @Step("{method} /" + ShopperApiEndpoints.Shipments.ID)
        public static Response GET(String shipmentId) {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.Shipments.ID, shipmentId);
        }

        public static class Stocks {
            /**
             * Получаем стоки заказа
             */
            @Step("{method} /" + ShopperApiEndpoints.Shipments.STOCKS)
            public static Response GET(String shipmentId) {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Shipments.STOCKS, shipmentId);
            }
        }
        public static class MarketingSampleItems {
            //todo
        }
    }
    public static class Stores {
        public static class Offers {
            /**
             * Ищем товары
             */
            @Step("{method} /" + ShopperApiEndpoints.Stores.OFFERS)
            public static Response GET(int shopperSid, String query) {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Stores.OFFERS, shopperSid, query);
            }
        }
    }
    public static class Helpdesk {
        public static class Tickets {
            /**
             * Получаем тикеты хелпдеска
             */
            @Step("{method} /" + ShopperApiEndpoints.Helpdesk.TICKETS)
            public static Response GET(String shipmentId) {
                return givenWithAuth()
                        .get(ShopperApiEndpoints.Helpdesk.TICKETS, shipmentId);
            }
        }
    }
    public static class Routes {
        /**
         * Получаем маршруты
         */
        @Step("{method} /" + ShopperApiEndpoints.ROUTES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.ROUTES);
        }
    }
    public static class MarsToken {
        /**
         * Получаем марс токен (стоки метро)
         */
        @Step("{method} /" + ShopperApiEndpoints.MARS_TOKEN)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.MARS_TOKEN);
        }
    }
    public static class CancelReasons {
        /**
         * Получаем причины отмен
         */
        @Step("{method} /" + ShopperApiEndpoints.CANCEL_REASONS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.CANCEL_REASONS);
        }
    }
    public static class ClarifyReasons {
        /**
         * Получаем причины несоответсвия
         */
        @Step("{method} /" + ShopperApiEndpoints.CLARIFY_REASONS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.CLARIFY_REASONS);
        }
    }
    public static class ReturnReasons {
        /**
         * Получаем причины возврата
         */
        @Step("{method} /" + ShopperApiEndpoints.RETURN_REASONS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ShopperApiEndpoints.RETURN_REASONS);
        }
    }

}
