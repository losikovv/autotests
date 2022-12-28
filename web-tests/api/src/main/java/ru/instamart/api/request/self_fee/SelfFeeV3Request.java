package ru.instamart.api.request.self_fee;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.instamart.api.endpoint.SelfFreeEndpoints;
import ru.instamart.api.request.SelfFeeRequestBase;

import java.io.File;
import java.util.Map;

public class SelfFeeV3Request extends SelfFeeRequestBase {

    @Step("{method} /" + SelfFreeEndpoints.V3.UPLOAD)
    public static Response POST(Map<String, String> files) {
        RequestSpecification requestSpecification = givenWithAuth();
        files.forEach((k, v) -> requestSpecification.multiPart(k,  new File(v), "multipart/form-data"));

        return requestSpecification
                .post(SelfFreeEndpoints.V3.UPLOAD);
    }

    @Step("{method} /" + SelfFreeEndpoints.V3.Upload.BY_ID)
    public static Response GET(long id) {
        return givenWithAuth()
                .get(SelfFreeEndpoints.V3.Upload.BY_ID, id);
    }
}
