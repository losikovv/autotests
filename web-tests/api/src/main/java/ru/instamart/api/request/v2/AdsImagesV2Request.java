package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class AdsImagesV2Request extends ApiV2RequestBase {


    @Step("{method} /" + ApiV2EndPoints.ADS_IMAGES)
    public static Response GET(final String image) {
        return givenWithAuth()
                .queryParam("image_path", image)
                .get(ApiV2EndPoints.ADS_IMAGES);
    }

    @Step("{method} /" + ApiV2EndPoints.ADS_IMAGES)
    public static Response HEAD(final String image) {
        return givenWithAuth()
                .queryParam("image_path", image)
                .head(ApiV2EndPoints.ADS_IMAGES);
    }
}
