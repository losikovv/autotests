package ru.instamart.api.requests.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ShopperAppEndpoints;
import ru.instamart.api.requests.ShopperAppRequestBase;

import java.util.HashMap;
import java.util.Map;

public final class ReplacementsSHPRequest extends ShopperAppRequestBase {

    public static class AdditionalItems {
        /**
         * Допзамена для товара
         */
        @Step("{method} /" + ShopperAppEndpoints.Replacements.ADDITIONAL_ITEMS)
        public static Response POST(String replacementId, String offerUuid, int foundQty) {
            Map<String, Object> data = new HashMap<>();
            data.put("assembly_item[offer_uuid]", offerUuid);
            data.put("assembly_item[found_qty]", foundQty);

            return givenWithAuth()
                    .formParams(data)
                    .post(ShopperAppEndpoints.Replacements.ADDITIONAL_ITEMS, replacementId);
        }
    }
}
