package ru.instamart.tests.api.v2.endpoints;

import instamart.api.action.Users;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.User;
import instamart.api.responses.v2.ErrorResponse;
import instamart.api.responses.v2.UserResponse;
import instamart.core.listeners.ExecutionListenerImpl;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

//TODO переделать на датапровайдер
@Epic("ApiV2")
@Feature("Регистрация")
@Listeners(ExecutionListenerImpl.class)
public class RegistrationTest extends RestBase {

    private final String firstName = "autotester";
    private final String lastName = "api";
    private final String minCharPassword = "instam";

    @CaseId(138)
    @Test(groups = {"api-instamart-regress"})
    @Story("Успешная регистрация")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessRegistration() {
        final UserData userData = UserManager.getUser();

        final Response response = Users.POST(
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

        assertEquals(response.getStatusCode(), 200);
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
        assertEquals(userResponse.getFirst_name(), userData.getFirstName(), "Имя несоответствует");
        assertEquals(userResponse.getLast_name(), userData.getLastName(), "Фамилия несоответствует");
        assertTrue(userResponse.getPromo_terms_accepted(), "Promo terms несоответствует");
        assertTrue(userResponse.getPrivacy_terms(), "Privacy terms несоответствует");
        assertFalse(userResponse.getB2b(), "B2b несоответствует");
    }

    @CaseId(139)
    @Test(groups = {"api-instamart-regress"})
    @Story("Успешная регистрация без согласия на рассылку")
    @Severity(SeverityLevel.NORMAL)
    public void testSuccessRegistrationWithoutPromoAccept() {
        final UserData userData = UserManager.getUser();

        final Response response = Users.POST(
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

        assertEquals(response.getStatusCode(), 200);
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
        assertEquals(userResponse.getFirst_name(), userData.getFirstName(), "Имя несоответствует");
        assertEquals(userResponse.getLast_name(), userData.getLastName(), "Фамилия несоответствует");
        assertFalse(userResponse.getPromo_terms_accepted(), "Promo terms несоответствует");
        assertFalse(userResponse.getPrivacy_terms(), "Privacy terms несоответствует");
        assertFalse(userResponse.getB2b(), "B2b несоответствует");
    }

    @CaseId(140)
    @Test(groups = {"api-instamart-regress"})
    @Story("Успешная регистрация b2b")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessRegistrationB2b() {
        final UserData userData = UserManager.getUser();

        final Response response = Users.POST(
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

        assertEquals(response.getStatusCode(), 200);
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
        assertEquals(userResponse.getFirst_name(), userData.getFirstName(), "Имя несоответствует");
        assertEquals(userResponse.getLast_name(), userData.getLastName(), "Фамилия несоответствует");
        assertTrue(userResponse.getPromo_terms_accepted(), "Promo terms несоответствует");
        assertTrue(userResponse.getPrivacy_terms(), "Privacy terms несоответствует");
        assertTrue(userResponse.getB2b(), "B2b несоответствует");
    }

    @CaseId(141)
    @Test(groups = {"api-instamart-regress"})
    @Story("Успешная регистрация b2b без согласия на рассылку")
    @Severity(SeverityLevel.BLOCKER)
    public void testSuccessRegistrationB2bWithoutPromoAccept() {
        final UserData userData = UserManager.getUser();

        final Response response = Users.POST(
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

        assertEquals(response.getStatusCode(), 200);
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
        assertEquals(userResponse.getFirst_name(), userData.getFirstName(), "Имя несоответствует");
        assertEquals(userResponse.getLast_name(), userData.getLastName(), "Фамилия несоответствует");
        assertFalse(userResponse.getPromo_terms_accepted(), "Promo terms несоответствует");
        assertFalse(userResponse.getPrivacy_terms(), "Privacy terms несоответствует");
        assertTrue(userResponse.getB2b(), "B2b несоответствует");
    }

    @CaseId(142)
    @Test(groups = {"api-instamart-regress"})
    @Story("Неверный формат email")
    @Severity(SeverityLevel.NORMAL)
    public void wrongEmailFormat() {
        final Response response = Users.POST(
                "example.com",
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "Неверный формат email");
    }

    @CaseId(143)
    @Test(groups = {"api-instamart-regress"})
    @Story("Короткий пароль")
    @Severity(SeverityLevel.NORMAL)
    public void shortPassword() {
        final Response response = Users.POST(
                Generate.email(),
                firstName,
                lastName,
                "insta");

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "Не может быть короче 6 символов");
    }

    @Test(groups = {"api-instamart-regress"})
    @Story("Пустой email")
    @Severity(SeverityLevel.NORMAL)
    public void emptyEmail() {
        final Response response = Users.POST(
                "",
                firstName,
                lastName,
                minCharPassword);

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }

    @CaseId(145)
    @Test(groups = {"api-instamart-regress"})
    @Story("Пустое имя")
    @Severity(SeverityLevel.NORMAL)
    public void testWithInvalidFirstAndLastName() {
        final String email = Generate.email();

        final Response response = Users.POST(
                email,
                "",
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

        response = Users.POST(
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

        response = Users.POST(
                email,
                "autotester",
                "api",
                "");

        assertEquals(response.getStatusCode(), 422);
        assertEquals(response.as(ErrorResponse.class).getError_messages().get(0).getHuman_message(),
                "не может быть пустым");
    }

    @CaseId(146)
    @Test(groups = {"api-instamart-regress"})
    @Story("Успешная регистрация с указанием location")
    @Severity(SeverityLevel.NORMAL)
    public void testSuccessRegistrationWithLocation() {
        final UserData userData = UserManager.getUser();

        final Response response = Users.POST(
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

        assertEquals(response.getStatusCode(), 200);
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
        assertEquals(userResponse.getFirst_name(), userData.getFirstName(), "Имя несоответствует");
        assertEquals(userResponse.getLast_name(), userData.getLastName(), "Фамилия несоответствует");
        assertFalse(userResponse.getPromo_terms_accepted(), "Promo terms несоответствует");
        assertFalse(userResponse.getPrivacy_terms(), "Privacy terms несоответствует");
        assertTrue(userResponse.getB2b(), "B2b несоответствует");
    }

    @CaseId(149)
    @Test(groups = {"api-instamart-regress"})
    @Story("Успешная регистрация с указанием anonymousId")
    @Severity(SeverityLevel.NORMAL)
    public void testSuccessRegistrationWithAnonymousId() {
        final UserData userData = UserManager.getUser();

        final Response response = Users.POST(
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

        assertEquals(response.getStatusCode(), 200);
        final User userResponse = response.as(UserResponse.class).getUser();
        assertEquals(userResponse.getEmail(), userData.getLogin(), "Некорректный логин");
        assertEquals(userResponse.getFirst_name(), userData.getFirstName(), "Имя несоответствует");
        assertEquals(userResponse.getLast_name(), userData.getLastName(), "Фамилия несоответствует");
        assertFalse(userResponse.getPromo_terms_accepted(), "Promo terms несоответствует");
        assertFalse(userResponse.getPrivacy_terms(), "Privacy terms несоответствует");
        assertTrue(userResponse.getB2b(), "B2b несоответствует");
    }
}
