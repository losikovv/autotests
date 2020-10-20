package ru.instamart.tests.api.v2.endpoints;

import org.testng.annotations.Test;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.ErrorResponse;
import instamart.api.responses.v2.UsersResponse;

import static org.testng.Assert.assertEquals;
import static instamart.api.requests.ApiV2Requests.postUsers;

public class Users extends RestBase {
    private final String firstName = "autotester";
    private final String lastName = "api";
    private final String minCharPassword = "instam";

    @Test(
            description = "Успешная регистрация",
            groups = {"rest"},
            priority = 100
    )
    public void successRegistration() {
        String email = email();

        response = postUsers(
                email,
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UsersResponse.class).getUser().getEmail(), email);
    }

    @Test(
            description = "Неверный формат email",
            groups = {"rest"},
            priority = 101
    )
    public void wrongEmailFormat() {
        String email = "example.com";

        response = postUsers(
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
            groups = {"rest"},
            priority = 102
    )
    public void shortPassword() {
        String password = "insta";

        response = postUsers(
                email(),
                firstName,
                lastName,
                password);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "Не может быть короче 6 символов");
    }

    @Test(
            description = "пустой email",
            groups = {"rest"},
            priority = 103
    )
    public void emptyEmail() {
        response = postUsers(
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
            groups = {"rest"},
            priority = 104
    )
    public void emptyFirstName() {
        String email = email();

        response = postUsers(
                email,
                "",
                "api",
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UsersResponse.class).getUser().getEmail(), email);
    }

    @Test(
            description = "пустая фамилия",
            groups = {"rest"},
            priority = 105
    )
    public void emptyLastName() {
        String email = email();

        response = postUsers(
                email,
                "autotester",
                "",
                minCharPassword);

        assertEquals(response.getStatusCode(), 200);
        assertEquals(response.as(UsersResponse.class).getUser().getEmail(), email);
    }

    @Test(
            description = "пустые имя и фамилия",
            groups = {"rest"},
            priority = 106
    )
    public void emptyFirstAndLastNames() {
        String email = email();

        response = postUsers(
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
            groups = {"rest"},
            priority = 107
    )
    public void emptyPassword() {
        String email = email();

        response = postUsers(
                email,
                "autotester",
                "api",
                "");

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }
}
