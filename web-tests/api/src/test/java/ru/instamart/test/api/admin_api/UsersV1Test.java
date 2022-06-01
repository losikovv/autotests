package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v1.AdminUserV1;
import ru.instamart.api.request.v1.admin.UsersV1Request;
import ru.instamart.api.response.v1.admin.UserV1Response;
import ru.instamart.api.response.v1.admin.UsersV1Response;
import ru.instamart.jdbc.dao.stf.SpreeUsersDao;
import ru.instamart.kraken.data.Generate;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkUsers;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Пользователи")
public class UsersV1Test extends RestBase {

    private Long userId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApiWithAdminNewRoles();
    }


    @Test(description = "Получение пользователя по емейлу",
            groups = {"api-instamart-regress"},
            dataProvider = "userEmails",
            dataProviderClass = RestDataProvider.class)
    public void getUser(String email) {
        final Response response = UsersV1Request.GET(email);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, UsersV1Response.class);
        List<AdminUserV1> usersFromResponse = response.as(UsersV1Response.class).getUsers();
        final SoftAssert softAssert = new SoftAssert();
        usersFromResponse.forEach(u -> softAssert.assertTrue(u.getContactEmail().contains("autotest"), "Пришел неверный емейл"));
        softAssert.assertAll();
    }

    @Test(description = "Получение пользователя по номеру телефона",
            groups = {"api-instamart-regress"})
    public void getUserByPhone() {
        final Response response = UsersV1Request.GET("79000000001");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, UsersV1Response.class);
        List<AdminUserV1> usersFromResponse = response.as(UsersV1Response.class).getUsers();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(usersFromResponse.size(), 1, softAssert);
        usersFromResponse.forEach(u -> softAssert.assertTrue(u.getPhoneTokenNumber().contains("79000000001")));
        softAssert.assertAll();
    }

    @Test(description = "Создание пользователя",
            groups = {"api-instamart-regress"})
    public void createUser() {
        UsersV1Request.UserRequest user = UsersV1Request.UserRequest.builder()
                .user(UsersV1Request.User.builder()
                        .b2b(false)
                        .email(Generate.email())
                        .password("sbermarket")
                        .roleIds(List.of(1L, 48L, 55L))
                        .passwordConfirmation("sbermarket")
                        .customerComment(Generate.literalString(5))
                        .preferredCardPaymentMethod("card_courier")
                        .promoTermsAccepted(true)
                        .configAttributes(UsersV1Request.ConfigAttributes.builder()
                                .sendEmails(false)
                                .build())
                        .build())
                .build();
        final Response response = UsersV1Request.POST(user);
        checkStatusCode(response, 201);
        checkResponseJsonSchema(response, UserV1Response.class);
        AdminUserV1 userFromResponse = response.as(UserV1Response.class).getUser();
        userId = userFromResponse.getId();
        checkUsers(user, userFromResponse);
    }


    @Test(description = "Удаление пользователя",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createUser")
    public void deleteUser() {
        final Response response = UsersV1Request.DELETE(userId);
        checkStatusCode(response, 204);
        Assert.assertTrue(SpreeUsersDao.INSTANCE.findById(userId).isEmpty(), "Пользователь не удалился");
    }
}
