package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class AdsImages extends ApiV2RequestBase {


    @Step("{method} /" + ApiV2EndPoints.ADS_IMAGES)
    public static Response GET(String image) {
        return givenWithAuth()
                .get(ApiV2EndPoints.ADS_IMAGES, image);
    }

    @Step("{method} /" + ApiV2EndPoints.ADS_IMAGES)
    public static Response HEAD(String image) {
        return givenWithAuth()
                .head(ApiV2EndPoints.ADS_IMAGES, image);
    }
}
