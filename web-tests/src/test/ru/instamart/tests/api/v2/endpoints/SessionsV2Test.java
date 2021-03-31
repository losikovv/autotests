package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.enums.v2.AuthProvider;
import instamart.api.helpers.RegistrationHelper;
import instamart.api.requests.v2.SessionRequest;
import instamart.api.responses.v2.SessionsResponse;
import instamart.api.responses.v2.UserDataResponse;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Epic(value = "ApiV2")
@Feature(value = "Авторизация")
public final class SessionsV2Test extends RestBase {

    @CaseId(14)
    @Test(  dataProvider = "authProviders",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-smoke"})
    @Story("Авторизуемся через стороннего провайдера")
    @Severity(SeverityLevel.CRITICAL)
    public void postAuthProvidersSessions(final AuthProvider authProvider) {
        if (!EnvironmentData.INSTANCE.getServer().equalsIgnoreCase("production")) {
            throw new SkipException("Скипаем тесты не на проде");
        }
        final Response response = SessionRequest.POST(authProvider);
        checkStatusCode200(response);
        assertNotNull(response.as(SessionsResponse.class).getSession());
    }

    @CaseId(110)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Авторизуемся c Client-Id: InstamartApp (как мобильное приложение)")
    @Severity(SeverityLevel.CRITICAL)
    public void postSessionsInstamartApp() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        final Response response = SessionRequest.POST(userData.getLogin(), userData.getPassword(), "InstamartApp");
        checkStatusCode200(response, "Не работает авторизация с Client-Id: InstamartApp");
        assertNotNull(response.as(SessionsResponse.class).getSession());
    }

    @CaseId(161)
    @Test(groups = {"api-instamart-regress"})
    @Story("Авторизация с невалидным email")
    @Severity(SeverityLevel.MINOR)
    public void testInvalidAuth() {
        final Response response = SessionRequest.POST("email@invalid", "password");
        checkStatusCode401(response);
    }

    @CaseId(162)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Авторизация с валидным email")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidAuth() {
        final Response response = SessionRequest.POST(UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword());
        checkStatusCode200(response);
        final SessionsResponse sessionResponse = response.as(SessionsResponse.class);
        assertNotNull(sessionResponse);
        assertNotNull(sessionResponse.getSession().getAccessToken(), "Токен авторизации отсутствует");
    }

    @CaseId(180)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Валидация сессионного токена")
    @Severity(SeverityLevel.CRITICAL)
    public void testSessionToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.APIV2, userData);
        final Response response = SessionRequest.GET(SessionFactory.getSession(SessionType.APIV2).getToken());
        checkStatusCode200(response);
    }

    @CaseId(179)
    @Test(groups = {"api-instamart-regress"})
    @Story("Невалидные сессионный токен")
    @Severity(SeverityLevel.MINOR)
    public void testInvalidToken() {
        final Response response = SessionRequest.GET("aaaaaaaaa");
        checkStatusCode404(response);
    }

    @CaseId(182)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Валидация данных по сессионному токену")
    @Severity(SeverityLevel.CRITICAL)
    public void testUserData() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.APIV2, userData);
        final Response response = SessionRequest.UserSession.GET(SessionFactory.getSession(SessionType.APIV2).getToken());
        checkStatusCode200(response);
        final UserDataResponse userDataResponse = response.as(UserDataResponse.class);
        assertEquals(userDataResponse.getUser().getEmail(), userData.getLogin(), "Получены чужие данные");
    }

    @CaseId(181)
    @Test(groups = {"api-instamart-regress"})
    @Story("Валидация данных по сессионному токену")
    @Severity(SeverityLevel.NORMAL)
    public void testUserDataWithInvalidToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.APIV2, userData);
        final Response response = SessionRequest.UserSession.GET("aaaaaaa");
        checkStatusCode404(response);
    }
}
