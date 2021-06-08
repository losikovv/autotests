package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class DeliveryAvailabilityV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.DELIVERY_AVAILABILITY)
    public static Response GET(final String lat, final String lon) {
        return givenWithAuth()
                .get(ApiV2EndPoints.DELIVERY_AVAILABILITY, lat, lon);
    }
}
