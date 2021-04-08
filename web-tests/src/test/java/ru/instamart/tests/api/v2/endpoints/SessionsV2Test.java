package ru.instamart.tests.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.Test;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.helpers.RegistrationHelper;
import ru.instamart.api.requests.v2.SessionV2Request;
import ru.instamart.api.responses.v2.SessionsV2Response;
import ru.instamart.api.responses.v2.UserDataV2Response;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.testdata.dataprovider.RestDataProvider;
import ru.instamart.ui.common.pagesdata.EnvironmentData;
import ru.instamart.ui.common.pagesdata.UserData;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.*;

@Epic(value = "ApiV2")
@Feature(value = "Авторизация")
public final class SessionsV2Test extends RestBase {

    @CaseId(14)
    @Test(  dataProvider = "authProviders",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-prod"},
            description = "Авторизуемся через стороннего провайдера")
    public void postAuthProvidersSessions(final AuthProviderV2 authProvider) {
        if (!EnvironmentData.INSTANCE.getServer().equalsIgnoreCase("production")) {
            throw new SkipException("Скипаем тесты не на проде");
        }
        final Response response = SessionV2Request.POST(authProvider);
        checkStatusCode200(response);
        assertNotNull(response.as(SessionsV2Response.class).getSession());
    }

    @CaseId(110)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Авторизуемся c Client-Id: InstamartApp (как мобильное приложение)")
    public void postSessionsInstamartApp() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        final Response response = SessionV2Request.POST(userData.getLogin(), userData.getPassword(), "InstamartApp");
        checkStatusCode200(response, "Не работает авторизация с Client-Id: InstamartApp");
        assertNotNull(response.as(SessionsV2Response.class).getSession());
    }

    @CaseId(161)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Авторизация с невалидным email")
    public void testInvalidAuth() {
        final Response response = SessionV2Request.POST("email@invalid", "password");
        checkStatusCode401(response);
    }

    //todo починить тест для прода
    @CaseId(162)
    @Test(groups = {"api-instamart-smoke"}, description = "Авторизация с валидным email")
    public void testValidAuth() {
        final Response response = SessionV2Request.POST(UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
        checkStatusCode200(response);
        final SessionsV2Response sessionResponse = response.as(SessionsV2Response.class);
        assertNotNull(sessionResponse);
        assertNotNull(sessionResponse.getSession().getAccessToken(), "Токен авторизации отсутствует");
    }

    @CaseId(180)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Валидация сессионного токена")
    public void testSessionToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.APIV2, userData);
        final Response response = SessionV2Request.GET(SessionFactory.getSession(SessionType.APIV2).getToken());
        checkStatusCode200(response);
    }

    @CaseId(179)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Невалидные сессионный токен")
    public void testInvalidToken() {
        final Response response = SessionV2Request.GET("aaaaaaaaa");
        checkStatusCode404(response);
    }

    @CaseId(182)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Валидация данных по сессионному токену")
    public void testUserData() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.APIV2, userData);
        final Response response = SessionV2Request.UserSession.GET(SessionFactory.getSession(SessionType.APIV2).getToken());
        checkStatusCode200(response);
        final UserDataV2Response userDataResponse = response.as(UserDataV2Response.class);
        assertEquals(userDataResponse.getUser().getEmail(), userData.getLogin(), "Получены чужие данные");
    }

    @CaseId(181)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Валидация данных по сессионному токену")
    public void testUserDataWithInvalidToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.APIV2, userData);
        final Response response = SessionV2Request.UserSession.GET("aaaaaaa");
        checkStatusCode404(response);
    }
}
