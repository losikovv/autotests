package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.request.v2.SessionsV2Request;
import ru.instamart.api.response.v2.UserDataV2Response;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic(value = "ApiV2")
@Feature(value = "Авторизация")
public final class SessionsV2Test extends RestBase {

    @CaseId(180)
    @Test(  groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Валидация сессионного токена")
    public void testSessionToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.API_V2_FB, userData);
        final Response response = SessionsV2Request.GET(SessionFactory.getSession(SessionType.API_V2_FB).getToken());
        checkStatusCode200(response);
    }

    @CaseId(179)
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Невалидные сессионный токен")
    public void testInvalidToken() {
        final Response response = SessionsV2Request.GET("aaaaaaaaa");
        checkStatusCode404(response);
    }

    @CaseId(182)
    @Test(  groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Валидация данных по сессионному токену")
    public void testUserData() {
        final UserData userData = UserManager.getDefaultApiUser();
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, userData);
        final Response response = SessionsV2Request.User.GET(SessionFactory.getSession(SessionType.API_V2_PHONE).getToken());
        checkStatusCode200(response);
        final UserDataV2Response userDataResponse = response.as(UserDataV2Response.class);
        assertEquals(userDataResponse.getUser().getEmail(), userData.getLogin(), "Получены чужие данные");
    }

    @CaseId(181)
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Валидация данных по сессионному токену")
    public void testUserDataWithInvalidToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.API_V2_FB, userData);
        final Response response = SessionsV2Request.User.GET("aaaaaaa");
        checkStatusCode404(response);
    }
}
