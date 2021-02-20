package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.condition.SessionCondition;
import instamart.api.enums.v2.AuthProvider;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import org.testng.SkipException;
import org.testng.annotations.Test;

@Epic(value = "ApiV2")
@Feature(value = "Авторизация")
public final class SessionsTest extends RestBase {

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
        SessionCondition
                .newTest()
                .externalAuth(authProvider)
                .checkExternalAuth();
    }

    @CaseId(110)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Авторизуемся c Client-Id: InstamartApp (как мобильное приложение)")
    @Severity(SeverityLevel.CRITICAL)
    public void postSessionsInstamartApp() {
        SessionCondition
                .newTest()
                .authWithClientId("InstamartApp")
                .checkAuthWithClientId();
    }

    @CaseId(161)
    @Test(groups = {"api-instamart-regress"})
    @Story("Авторизация с невалидным email")
    @Severity(SeverityLevel.MINOR)
    public void testInvalidAuth() {
        SessionCondition
                .newTest()
                .authWithInvalidData("email@invalid", "password")
                .checkInvalidAuth();
    }

    @CaseId(162)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Авторизация с валидным email")
    @Severity(SeverityLevel.BLOCKER)
    public void testValidAuth() {
        SessionCondition
                .newTest()
                .authWithValidData(UserManager.getDefaultUser().getLogin(), UserManager.getDefaultUser().getPassword())
                .checkValidAuth();
    }

    @CaseId(180)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Валидация сессионного токена")
    @Severity(SeverityLevel.CRITICAL)
    public void testSessionToken() {
        SessionCondition
                .newTest()
                .registrationAndAuth(UserManager.getUser())
                .callSessionValidation(SessionFactory.getSession().getToken())
                .checkSessionToken();
    }

    @CaseId(179)
    @Test(groups = {"api-instamart-regress"})
    @Story("Невалидные сессионный токен")
    @Severity(SeverityLevel.MINOR)
    public void testInvalidToken() {
        SessionCondition
                .newTest()
                .callSessionValidation("aaaaaaaaa")
                .checkInvalidSessionToken();
    }

    @CaseId(182)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Валидация данных по сессионному токену")
    @Severity(SeverityLevel.CRITICAL)
    public void testUserData() {
        final UserData userData = UserManager.getUser();
        SessionCondition
                .newTest()
                .registrationAndAuth(userData)
                .getUserData(SessionFactory.getSession().getToken())
                .checkUserData(userData);
    }

    @CaseId(181)
    @Test(groups = {"api-instamart-regress"})
    @Story("Валидация данных по сессионному токену")
    @Severity(SeverityLevel.NORMAL)
    public void testUserDataWithInvalidToken() {
        final UserData userData = UserManager.getUser();
        SessionCondition
                .newTest()
                .registrationAndAuth(userData)
                .getUserData("aaaaaaa")
                .checkUserDataWithInvalidToken();
    }
}
