package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.TokensV1Request;
import ru.instamart.api.response.v1.TokensV1Response;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV1")
@Feature("Токены")
public class TokensV1Tests extends RestBase {

    @TmsLink("2367")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            description = "Получение токенов пользователя")
    public void getTokens() {
        admin.authApi();
        final Response response = TokensV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TokensV1Response.class);
    }

    @TmsLink("2368")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            description = "Получение токенов неавторизованного пользователя")
    public void getTokensWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = TokensV1Request.GET();
        checkStatusCode401(response);
        checkErrorText(response, "Пользователь не авторизован для этого действия");
    }
}
