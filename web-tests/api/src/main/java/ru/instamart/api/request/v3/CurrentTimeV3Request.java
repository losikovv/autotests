package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

import static ru.instamart.api.helper.ApiV3Helper.getApiClientTokenWithProd;

public class CurrentTimeV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.CURRENT_TIME)
    public static Response GET() {
        return givenWithAuth(getApiClientTokenWithProd(ClientV3.SBER_DEVICES))
                .get(ApiV3Endpoints.CURRENT_TIME);
    }
}
