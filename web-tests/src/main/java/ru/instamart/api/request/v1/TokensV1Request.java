package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.model.v1.ShoppersBackendV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.api.response.v1.TokensV1Response;

public class TokensV1Request extends ApiV1RequestBase {
    /**
     * Получение токенов для админки шоппера
     */
    @Step("{method} /" + ApiV1Endpoints.TOKENS)
    public static Response GET() {
        Response response = givenWithAuth()
                .get(ApiV1Endpoints.TOKENS);
        ShoppersBackendV1 shoppersBackend = response.as(TokensV1Response.class).getShoppersBackend();
        SessionFactory.getSession(SessionType.SHOPPER_APP)
                .setToken("token=" + shoppersBackend.getClientJwt() + ", id=" + shoppersBackend.getClientId());
        return response;
    }
}
