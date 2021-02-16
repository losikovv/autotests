package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.responses.v2.ErrorResponse;
import instamart.core.testdata.ui.Generate;
import org.testng.annotations.Test;
import instamart.api.responses.v2.UserResponse;

import static instamart.api.requests.ApiV2Requests.Users.POST;
import static org.testng.Assert.assertEquals;
//TODO переделать на датапровайдер
public class Users extends RestBase {
    private final String firstName = "autotester";
    private final String lastName = "api";
    private final String minCharPassword = "instam";

    @Test(
            description = "Успешная регистрация",
            groups = {"api-instamart-regress"}
    )
    public void successRegistration() {
        final String email = Generate.email();

        response = POST(
                email,
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UserResponse.class).getUser().getEmail(), email);
    }

    @Test(
            description = "Неверный формат email",
            groups = {"api-instamart-regress"}
    )
    public void wrongEmailFormat() {
        String email = "example.com";

        response = POST(
                email,
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "Неверный формат email");
    }

    @Test(
            description = "короткий пароль",
            groups = {"api-instamart-regress"}
    )
    public void shortPassword() {
        String password = "insta";

        response = POST(
                Generate.email(),
                firstName,
                lastName,
                password);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "Не может быть короче 6 символов");
    }

    @Test(
            description = "пустой email",
            groups = {"api-instamart-regress"}
    )
    public void emptyEmail() {
        response = POST(
                "",
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }

    @Test(
            description = "пустое имя",
            groups = {"api-instamart-regress"}
    )
    public void emptyFirstName() {
        final String email = Generate.email();

        response = POST(
                email,
                "",
                "api",
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UserResponse.class).getUser().getEmail(), email);
    }

    @Test(
            description = "пустая фамилия",
            groups = {"api-instamart-regress"}
    )
    public void emptyLastName() {
        final String email = Generate.email();

        response = POST(
                email,
                "autotester",
                "",
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UserResponse.class).getUser().getEmail(), email);
    }

    @Test(
            description = "пустые имя и фамилия",
            groups = {"api-instamart-regress"}
    )
    public void emptyFirstAndLastNames() {
        final String email = Generate.email();

        response = POST(
                email,
                "",
                "",
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }

    @Test(
            description = "пустой пароль",
            groups = {"api-instamart-regress"}
    )
    public void emptyPassword() {
        final String email = Generate.email();

        response = POST(
                email,
                "autotester",
                "api",
                "");

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }
}
