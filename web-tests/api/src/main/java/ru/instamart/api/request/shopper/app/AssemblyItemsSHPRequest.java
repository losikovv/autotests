package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShopperAppEndpoints;
import ru.instamart.api.request.ShopperAppRequestBase;

@SuppressWarnings("unchecked")
public final class AssemblyItemsSHPRequest extends ShopperAppRequestBase {

    /**
     * Собираем товар
     */
    @Step("{method} /" + ShopperAppEndpoints.Assemblies.Items.ID)
    public static Response PATCH(String assemblyId, String itemId, int itemQty) {
        JSONObject requestParams = new JSONObject();
        JSONObject assemblyItem = new JSONObject();
        requestParams.put("assembly_item", assemblyItem);
        assemblyItem.put("found_qty", itemQty);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .patch(ShopperAppEndpoints.Assemblies.Items.ID, assemblyId, itemId);
    }

    /**
     * Собираем товар
     */
    @Step("{method} /" + ShopperAppEndpoints.Assemblies.Items.ID)
    public static Response PUT(String assemblyId, String itemId, int itemQty) {
        JSONObject requestParams = new JSONObject();
        JSONObject assemblyItem = new JSONObject();
        requestParams.put("assembly_item", assemblyItem);
        assemblyItem.put("found_qty", itemQty);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .patch(ShopperAppEndpoints.Assemblies.Items.ID, assemblyId, itemId);
    }


    public static class Approve {
        /**
         * Подтверждаем товар
         */
        @Step("{method} /" + ShopperAppEndpoints.AssemblyItems.APPROVE)
        public static Response PATCH(String itemId) {
            return givenWithAuth()
                    .patch(ShopperAppEndpoints.AssemblyItems.APPROVE, itemId);
        }
    }
    public static class Cancellations {
        /**
         * Отменяем товар
         */
        @Step("{method} /" + ShopperAppEndpoints.AssemblyItems.CANCELLATIONS)
        public static Response POST(String itemId, int reasonId) {
            return givenWithAuth()
                    .formParam("cancel_reason_id", reasonId)
                    .post(ShopperAppEndpoints.AssemblyItems.CANCELLATIONS, itemId);
        }
    }
    public static class Replacements {
        /**
         * Заменяем товар
         */
        @Step("{method} /" + ShopperAppEndpoints.AssemblyItems.REPLACEMENTS)
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
                    .post(ShopperAppEndpoints.AssemblyItems.REPLACEMENTS, itemId);
        }
    }
    public static class Clarifications {
        /**
         * Уточняем товар
         */
        @Step("{method} /" + ShopperAppEndpoints.AssemblyItems.CLARIFICATIONS)
        public static Response POST(String itemId, int reasonId) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("reason_id", reasonId);
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.AssemblyItems.CLARIFICATIONS, itemId);
        }
    }
    public static class LogNotFoundEans {
        /**
         * Уточняем товар
         */
        @Step("{method} /" + ShopperAppEndpoints.AssemblyItems.LOG_NOT_FOUND_EANS)
        public static Response POST(String itemId, int reasonId) {
            JSONObject requestParams = new JSONObject();
            requestParams.put("reason_id", reasonId);
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ShopperAppEndpoints.AssemblyItems.CLARIFICATIONS, itemId);
        }
    }
    public static class Prereplacements {
        /**
         * Получаем предзамены для товара
         */
        @Step("{method} /" + ShopperAppEndpoints.AssemblyItems.PREREPLACEMENTS)
        public static Response GET(String itemId) {
            return givenWithAuth()
                    .get(ShopperAppEndpoints.AssemblyItems.PREREPLACEMENTS, itemId);
        }
    }
}
