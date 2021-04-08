package ru.instamart.api.requests.shopper;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperApiEndpoints;

import java.util.HashMap;
import java.util.Map;

import static ru.instamart.api.requests.ShopperRequestBase.givenWithAuth;

public final class ReplacementsSHPRequest {

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
