package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class AssemblyItemsRequest {

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
