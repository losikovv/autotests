package ru.instamart.api.request.self_fee;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.SelfFreeEndpoints;
import ru.instamart.api.request.SelfFeeRequestBase;

public class SelfFeeV1Request extends SelfFeeRequestBase {

    @Step("{method} /" + SelfFreeEndpoints.V1.REGISTRY)
    public static Response GET() {
        return givenWithAuth()
                .get(SelfFreeEndpoints.V1.REGISTRY);
    }
}
