package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class PromotionsV1Request extends ApiV1RequestBase {

    public static class FreeDelivery {
        @Step("{method} /" + ApiV1Endpoints.Promotions.FREE_DELIVERY)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Promotions.FREE_DELIVERY);
        }
    }

    public static class CompensationPromotions {
        @Step("{method} /" + ApiV1Endpoints.COMPENSATION_PROMOTIONS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.COMPENSATION_PROMOTIONS);
        }
    }

    public static class Promotions {
        @Step("{method} /" + ApiV1Endpoints.PROMOTIONS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.PROMOTIONS);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.Promotions.PROMOTION)
        public static Response GET(Long promotionId) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.Promotions.PROMOTION, promotionId);
        }
    }
}
