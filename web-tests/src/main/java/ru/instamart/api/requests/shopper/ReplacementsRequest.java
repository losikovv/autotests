package instamart.api.requests.shopper;

import instamart.api.endpoints.ShopperApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class ReplacementsRequest {

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
