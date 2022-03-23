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
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV1")
@Feature("Токены")
public class TokensV1Tests extends RestBase {

    @CaseId(2367)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение токенов пользователя")
    public void getTokens() {
        admin.authApi();
        final Response response = TokensV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TokensV1Response.class);
    }

    @CaseId(2368)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение токенов неавторизованного пользователя")
    public void getTokensWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V1);
        final Response response = TokensV1Request.GET();
        checkStatusCode401(response);
        checkErrorText(response, "Пользователь не авторизован для этого действия");
    }
}
