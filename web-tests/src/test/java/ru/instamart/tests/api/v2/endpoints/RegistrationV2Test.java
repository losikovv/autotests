package ru.instamart.tests.api.v2.endpoints;

import ru.instamart.api.common.RestBase;
import ru.instamart.api.objects.v2.User;
import ru.instamart.api.requests.v2.UsersRequest;
import ru.instamart.api.responses.ErrorResponse;
import ru.instamart.api.responses.v2.UserResponse;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.testdata.ui.Generate;
import ru.instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode422;
import static org.testng.Assert.assertEquals;

//TODO переделать на датапровайдер
@Epic("ApiV2")
@Feature("Регистрация")
public final class RegistrationV2Test extends RestBase {

    private final String firstName = "autotester";
    private final String lastName = "api";
    private final String minCharPassword = "instam";

    @CaseId(138)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Успешная регистрация")
    public void testSuccessRegistration() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersRequest.POST(
                userData.getLogin(),
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
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
    }

    @CaseId(139)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Успешная регистрация без согласия на рассылку")
    public void testSuccessRegistrationWithoutPromoAccept() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersRequest.POST(
                userData.getLogin(),
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
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
    }

    @CaseId(140)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Успешная регистрация b2b")
    public void testSuccessRegistrationB2b() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersRequest.POST(
                userData.getLogin(),
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
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
    }

    @CaseId(141)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Успешная регистрация b2b без согласия на рассылку")
    public void testSuccessRegistrationB2bWithoutPromoAccept() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersRequest.POST(
                userData.getLogin(),
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
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
    }

    @CaseId(142)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Неверный формат email")
    public void wrongEmailFormat() {
        final Response response = UsersRequest.POST(
                "example.com",
                firstName,
                lastName,
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "Неверный формат email");
    }

    @CaseId(143)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Короткий пароль")
    public void shortPassword() {
        final Response response = UsersRequest.POST(
                Generate.email(),
                firstName,
                lastName,
                "insta");

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "Не может быть короче 6 символов");
    }

    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Пустой email")
    public void emptyEmail() {
        final Response response = UsersRequest.POST(
                "",
                firstName,
                lastName,
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым");
    }

    @CaseId(145)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Пустое имя")
    public void testWithInvalidFirstAndLastName() {
        final String email = Generate.email();

        final Response response = UsersRequest.POST(
                email,
                "",
                "",
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым");
    }

    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Пустые имя и фамилия")
    public void emptyFirstAndLastNames() {
        final String email = Generate.email();

        response = UsersRequest.POST(
                email,
                "",
                "",
                minCharPassword);

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым");
    }

    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Пустой пароль")
    public void emptyPassword() {
        final String email = Generate.email();

        response = UsersRequest.POST(
                email,
                "autotester",
                "api",
                "");

        checkStatusCode422(response);
        assertEquals(response.as(ErrorResponse.class).getErrorMessages().get(0).getHumanMessage(),
                "не может быть пустым");
    }

    @CaseId(146)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Успешная регистрация с указанием location")
    public void testSuccessRegistrationWithLocation() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersRequest.POST(
                userData.getLogin(),
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
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
    }

    @CaseId(149)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Успешная регистрация с указанием anonymousId")
    public void testSuccessRegistrationWithAnonymousId() {
        final UserData userData = UserManager.getUser();

        final Response response = UsersRequest.POST(
                userData.getLogin(),
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
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
    }
}
