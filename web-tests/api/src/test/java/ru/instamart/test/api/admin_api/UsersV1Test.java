package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.AdminUserV1;
import ru.instamart.api.request.v1.admin.UsersV1Request;
import ru.instamart.api.response.v1.admin.UsersV1Response;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Пользователи")
public class UsersV1Test extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @Test(description = "Получение пользователя по частичному совпадению емейла", //TODO: добавить дата провайдер для полного емейла
            groups = {"api-instamart-regress"})
    public void getUser() {
        final Response response = UsersV1Request.GET("autotest");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, UsersV1Response.class);
        List<AdminUserV1> usersFromResponse = response.as(UsersV1Response.class).getUsers();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(usersFromResponse.size(), 10, softAssert);
        usersFromResponse.forEach(u -> softAssert.assertTrue(u.getContactEmail().contains("autotest"), "Пришел неверный емейл"));
        softAssert.assertAll();
    }
}
