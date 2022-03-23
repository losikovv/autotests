package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class UsersV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Users.Shipments.BY_NUMBER)
    public static Response GET(String userId, String shipmentNumber) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Users.Shipments.BY_NUMBER, userId, shipmentNumber);
    }

    @Step("{method} /" + ApiV1Endpoints.USERS)
    public static Response GET(String userEmail) {
        return givenWithAuth()
                .queryParam("email", userEmail)
                .get(ApiV1Endpoints.USERS);
    }
}
