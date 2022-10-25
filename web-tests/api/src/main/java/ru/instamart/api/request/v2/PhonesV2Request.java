package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.Map;

public final class PhonesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.PHONES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.PHONES);
    }

    public static Response POST(Map<String, String> params){
        return givenWithAuth()
                .params(params)
                .post(ApiV2EndPoints.PHONES);
    }

    public static class PhonesById {
        @Step("{method} /" + ApiV2EndPoints.PHONE)
        public static Response GET(String phoneId) {
            return givenWithAuth()
                    .get(ApiV2EndPoints.PHONE, phoneId);
        }

        @Step("{method} /" + ApiV2EndPoints.PHONE)
        public static Response PUT(String phoneId, Map<String, String> params) {
            return givenWithAuth()
                    .params(params)
                    .put(ApiV2EndPoints.PHONE, phoneId);
        }

        @Step("{method} /" + ApiV2EndPoints.PHONE)
        public static Response DELETE(String phonesId){
            return givenWithAuth()
                    .delete(ApiV2EndPoints.PHONE, phonesId);
        }
    }
}