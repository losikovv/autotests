package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.UsersV1Request;
import ru.instamart.api.response.v1.UsersV1Response;
import ru.instamart.kraken.data.user.UserData;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;

@Epic("ApiV1")
@Feature("Пользователи")
public class UsersV1Tests extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @TmsLink("2369")
    @Test(groups = {"api-instamart-smoke", API_INSTAMART_PROD, "api-v1"},
            description = "Получение данных пользователя по емейлу")
    public void getUsers() {
        UserData user = SessionFactory.getSession(SessionType.API_V1).getUserData();
        final Response response = UsersV1Request.GET(user.getEmail());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, UsersV1Response.class);
        compareTwoObjects(response.as(UsersV1Response.class).getUsers().get(0).getEmail(), user.getEmail());
    }

    @TmsLink("2370")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            description = "Получение данных пользователя с пустым емейлом")
    public void getUsersWithEmptyEmail() {
        final Response response = UsersV1Request.GET("");
        checkStatusCode400(response);
        checkErrorText(response, "Не указан обязательный параметр email");
    }

    @TmsLink("2371")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"},
            description = "Получение данных пользователя с несуществующим емейлом")
    public void getUsersWithNonExistentEmail() {
        final Response response = UsersV1Request.GET(String.format("random%s@test.com", RandomUtils.nextInt(1, 1000)));
        checkStatusCode200(response);
        Assert.assertTrue(response.as(UsersV1Response.class).getUsers().isEmpty(), "Вернулись пользователи для несуществующего емейла");
    }
}
