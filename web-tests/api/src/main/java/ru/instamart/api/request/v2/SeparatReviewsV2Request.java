package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class SeparatReviewsV2Request extends ApiV2RequestBase {

    public static class ReviewableShipment{

        @Step("{method} /" + ApiV2EndPoints.SeparateReviews.REVIEWABLE_SHIPMENT)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.SeparateReviews.REVIEWABLE_SHIPMENT);
        }
    }
}
