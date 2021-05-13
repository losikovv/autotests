package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.core.testdata.UserManager;

public class ShoppersV1Request extends ApiV1RequestBase {
    public static class MarketingSampleItems {
        @Step("{method} /" + ApiV1Endpoints.Shoppers.MARKETING_SAMPLE_ITEMS)
        public static Response GET(String shipmentUuid) {
            return givenWithSpec()
                    .header("X-Spree-Token", UserManager.getDefaultAdmin().getToken())
                    .get(ApiV1Endpoints.Shoppers.MARKETING_SAMPLE_ITEMS, shipmentUuid);
        }
    }

    public static class OrderAvailablePaymentTools {
        @Step("{method} /" + ApiV1Endpoints.Shoppers.ORDER_AVAILABLE_PAYMENT_TOOLS)
        public static Response GET(String orderNumber) {
            return givenWithSpec()
                    .header("X-Spree-Token", UserManager.getDefaultAdmin().getToken())
                    .get(ApiV1Endpoints.Shoppers.ORDER_AVAILABLE_PAYMENT_TOOLS, orderNumber);
        }
    }
}
