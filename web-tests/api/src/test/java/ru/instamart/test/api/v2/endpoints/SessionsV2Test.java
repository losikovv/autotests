package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.request.v2.SessionsV2Request;
import ru.instamart.api.response.v2.UserDataV2Response;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic(value = "ApiV2")
@Feature(value = "Авторизация")
public final class SessionsV2Test extends RestBase {

    @Deprecated
    @Test(  groups = {},
            description = "Валидация сессионного токена")
    public void testSessionToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.API_V2_FB, userData);
        final Response response = SessionsV2Request.GET(SessionFactory.getSession(SessionType.API_V2_FB).getToken());
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(  groups = {},
            description = "Невалидные сессионный токен")
    public void testInvalidToken() {
        final Response response = SessionsV2Request.GET("aaaaaaaaa");
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(  groups = {},
            description = "Валидация данных по сессионному токену")
    public void testUserData() {
        final UserData userData = UserManager.getDefaultApiUser();
        SessionFactory.createSessionToken(SessionType.API_V2_PHONE, userData);
        final Response response = SessionsV2Request.User.GET(SessionFactory.getSession(SessionType.API_V2_PHONE).getToken());
        checkStatusCode200(response);
        final UserDataV2Response userDataResponse = response.as(UserDataV2Response.class);
        assertEquals(userDataResponse.getUser().getEmail(), userData.getEmail(), "Получены чужие данные");
    }

    @Deprecated
    @Test(  groups = {},
            description = "Валидация данных по сессионному токену")
    public void testUserDataWithInvalidToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.API_V2_FB, userData);
        final Response response = SessionsV2Request.User.GET("aaaaaaa");
        checkStatusCode404(response);
    }
}
