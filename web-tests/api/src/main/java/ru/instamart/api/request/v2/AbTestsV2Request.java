package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class AbTestsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.AB_TESTS)
    public static Response GET(){
        return givenWithAuth()
                .get(ApiV2EndPoints.AB_TESTS);
    }

    @Step("{method} /" + ApiV2EndPoints.AbTests.DEVICE_ID)
    public static Response GET(final String deviceId){
        return givenWithAuth()
                .get(ApiV2EndPoints.AbTests.DEVICE_ID, deviceId);
    }

}
