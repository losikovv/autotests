package ru.instamart.tests.api.v2.endpoints;

import instamart.api.action.Registration;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.ErrorResponse;
import instamart.api.responses.v2.UserResponse;
import instamart.core.testdata.ui.Generate;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
//TODO переделать на датапровайдер
@Epic("ApiV2")
@Feature("Регистрация")
public class RegistrationTest extends RestBase {

    private final String firstName = "autotester";
    private final String lastName = "api";
    private final String minCharPassword = "instam";

    @Test(groups = {"api-instamart-regress"})
    @Story("Успешная регистрация")
    @Severity(SeverityLevel.BLOCKER)
    public void successRegistration() {
        final String email = Generate.email();

        response = Registration.POST(
                email,
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UserResponse.class).getUser().getEmail(), email);
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Неверный формат email")
    @Severity(SeverityLevel.NORMAL)
    public void wrongEmailFormat() {
        String email = "example.com";

        response = Registration.POST(
                email,
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "Неверный формат email");
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Короткий пароль")
    @Severity(SeverityLevel.NORMAL)
    public void shortPassword() {
        String password = "insta";

        response = Registration.POST(
                Generate.email(),
                firstName,
                lastName,
                password);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "Не может быть короче 6 символов");
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Пустой email")
    @Severity(SeverityLevel.NORMAL)
    public void emptyEmail() {
        response = Registration.POST(
                "",
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Пустое имя")
    @Severity(SeverityLevel.NORMAL)
    public void emptyFirstName() {
        final String email = Generate.email();

        response = Registration.POST(
                email,
                "",
                "api",
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UserResponse.class).getUser().getEmail(), email);
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Пустая фамилия")
    @Severity(SeverityLevel.NORMAL)
    public void emptyLastName() {
        final String email = Generate.email();

        response = Registration.POST(
                email,
                "autotester",
                "",
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UserResponse.class).getUser().getEmail(), email);
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Пустые имя и фамилия")
    @Severity(SeverityLevel.NORMAL)
    public void emptyFirstAndLastNames() {
        final String email = Generate.email();

        response = Registration.POST(
                email,
                "",
                "",
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Пустой пароль")
    @Severity(SeverityLevel.NORMAL)
    public void emptyPassword() {
        final String email = Generate.email();

        response = Registration.POST(
                email,
                "autotester",
                "api",
                "");

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }
}
