package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.request.v2.SessionsV2Request;
import ru.instamart.api.response.v2.UserDataV2Response;
import ru.instamart.jdbc.dao.stf.SpreeUsersDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic(value = "ApiV2")
@Feature(value = "Авторизация")
public final class SessionsV2Test extends RestBase {

    @Deprecated
    @Test(groups = {},
            description = "Валидация сессионного токена")
    public void testSessionToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.API_V2, userData);
        final Response response = SessionsV2Request.GET(SessionFactory.getSession(SessionType.API_V2).getToken());
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Невалидные сессионный токен")
    public void testInvalidToken() {
        final Response response = SessionsV2Request.GET("aaaaaaaaa");
        checkStatusCode404(response);
    }

    @CaseId(1436)
    @Story("Валидация данных по сессионному токену")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Токен существующего пользователя")
    public void testUserData() {
        final UserData userData = UserManager.getDefaultApiUser();
        apiV2.authByPhone(userData);
        final Response response = SessionsV2Request.User.GET(SessionFactory.getSession(SessionType.API_V2).getToken());
        checkStatusCode200(response);
        final UserDataV2Response userDataResponse = response.as(UserDataV2Response.class);
        compareTwoObjects(userDataResponse.getUser().getEmail(), SpreeUsersDao.INSTANCE.getEmailByPhone(userData.getPhone()));
    }

    @CaseId(1437)
    @Story("Валидация данных по сессионному токену")
    @Test(groups = {"api-instamart-regress"},
            description = "Невалидный токен")
    public void testUserDataWithInvalidToken() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        SessionFactory.createSessionToken(SessionType.API_V2, userData);
        final Response response = SessionsV2Request.User.GET("aaaaaaa");
        checkStatusCode404(response);
    }
}
