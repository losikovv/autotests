package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v2.UserV2;
import ru.instamart.api.request.v2.UsersV2Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.v2.UserV2Response;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode422;

@Epic("ApiV2")
@Feature("Регистрация")
@Deprecated
public final class RegistrationV2Test extends RestBase {

    private final String firstName = "autotester";
    private final String lastName = "api";
    private final String minCharPassword = "instam";

    @Deprecated
    @Test(groups = {}, description = "Успешная регистрация")
    public void testSuccessRegistration() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersV2Request.POST(
                userData.getEmail(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPassword(),
                null,
                true,
                true,
                0,
                false
                );

        checkStatusCode200(response);
        final UserV2 userResponse = response.as(UserV2Response.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getEmail(), "Некорректный логин");
    }

    @Deprecated
    @Test(groups = {}, description = "Успешная регистрация без согласия на рассылку")
    public void testSuccessRegistrationWithoutPromoAccept() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersV2Request.POST(
                userData.getEmail(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPassword(),
                null,
                false,
                false,
                0,
                false
        );

        checkStatusCode200(response);
        final UserV2 userResponse = response.as(UserV2Response.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getEmail(), "Некорректный логин");
    }

    @Deprecated
    @Test(groups = {}, description = "Успешная регистрация b2b")
    public void testSuccessRegistrationB2b() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersV2Request.POST(
                userData.getEmail(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPassword(),
                null,
                true,
                true,
                0,
                true
        );

        checkStatusCode200(response);
        final UserV2 userResponse = response.as(UserV2Response.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getEmail(), "Некорректный логин");
    }

    @Deprecated
    @Test(groups = {}, description = "Успешная регистрация b2b без согласия на рассылку")
    public void testSuccessRegistrationB2bWithoutPromoAccept() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersV2Request.POST(
                userData.getEmail(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPassword(),
                null,
                false,
                false,
                0,
                true
        );

        checkStatusCode200(response);
        final UserV2 userResponse = response.as(UserV2Response.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getEmail(), "Некорректный логин");
    }

    @Deprecated
    @Test(groups = {}, description = "Неверный формат email")
    public void wrongEmailFormat() {
        final Response response = UsersV2Request.POST(
                "example.com",
                firstName,
                lastName,
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "Неверный формат email", "Невалидная ошибка");
    }

    @Deprecated
    @Test(groups = {}, description = "Короткий пароль")
    public void shortPassword() {
        final Response response = UsersV2Request.POST(
                Generate.email(),
                firstName,
                lastName,
                "insta");

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "Не может быть короче 6 символов", "Невалидная ошибка");
    }

    @Deprecated
    @Test(groups = {}, description = "Пустой email")
    public void emptyEmail() {
        final Response response = UsersV2Request.POST(
                "",
                firstName,
                lastName,
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым", "Невалидная ошибка");
    }

    @Deprecated
    @Test(groups = {}, description = "Пустое имя")
    public void testWithInvalidFirstAndLastName() {
        final String email = Generate.email();

        final Response response = UsersV2Request.POST(
                email,
                "",
                "",
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым", "Невалидная ошибка");
    }

    @Deprecated
    @Test(groups = {}, description = "Пустые имя и фамилия")
    public void emptyFirstAndLastNames() {
        final String email = Generate.email();

        response = UsersV2Request.POST(
                email,
                "",
                "",
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым", "Невалидная ошибка");
    }

    @Deprecated
    @Test(groups = {}, description = "Пустой пароль")
    public void emptyPassword() {
        final String email = Generate.email();

        response = UsersV2Request.POST(
                email,
                "autotester",
                "api",
                "");

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым", "Невалидная ошибка");
    }

    @Deprecated
    @Test(groups = {}, description = "Успешная регистрация с указанием location")
    public void testSuccessRegistrationWithLocation() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersV2Request.POST(
                userData.getEmail(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPassword(),
                "location",
                false,
                false,
                0,
                true
        );

        checkStatusCode200(response);
        final UserV2 userResponse = response.as(UserV2Response.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getEmail(), "Некорректный логин");
    }

    @Deprecated
    @Test(groups = {}, description = "Успешная регистрация с указанием anonymousId")
    public void testSuccessRegistrationWithAnonymousId() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersV2Request.POST(
                userData.getEmail(),
                userData.getFirstName(),
                userData.getLastName(),
                userData.getPassword(),
                null,
                false,
                false,
                100,
                true
        );

        checkStatusCode200(response);
        final UserV2 userResponse = response.as(UserV2Response.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getEmail(), "Некорректный логин");
    }
}
