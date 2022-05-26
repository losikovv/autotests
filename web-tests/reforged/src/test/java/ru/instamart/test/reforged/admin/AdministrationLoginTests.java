package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.main;

@Epic("Админка STF")
@Feature("Страница логина")
public final class AdministrationLoginTests {

    @CaseId(440)
    @Story("Тест неуспешной авторизации с пустыми полями")
    @Test(description = "Тест неуспешной авторизации с пустыми полями", groups = "regression")
    public void noAuthWithEmptyFields() {
        login().goToPage();
        login().setUsername("");
        login().setPassword("");
        login().submit();
        login().checkErrorEmptyEmail();
        login().checkErrorEmptyPassword();
    }

    @CaseId(441)
    @Story("Тест неуспешной авторизации с некорректным логином")
    @Test(description = "Тест неуспешной авторизации с некорректным логином", groups = "regression")
    public void noAuthWithIncorrectUsername() {
        login().goToPage();
        login().setUsername("wrongUsername");
        login().setPassword("123456");
        login().submit();
        login().checkErrorInvalidEmail();
    }

    @CaseId(442)
    @Story("Тест неуспешной авторизации с несуществующим логином")
    @Test(description = "Тест неуспешной авторизации с несуществующим логином", groups = "regression")
    public void noAuthWithNonExistingUser() {
        login().goToPage();
        login().setUsername("nonexistinguser@instamart.ru");
        login().setPassword("123456");
        login().submit();
        login().checkErrorInvalidEmailOrPassword();
    }

    @CaseId(443)
    @Story("Тест неуспешной авторизации с коротким паролем")
    @Test(description = "Тест неуспешной авторизации с коротким паролем", groups = "regression")
    public void noAuthWithShortPassword() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultUser().getEmail());
        login().setPassword("123");
        login().submit();
        login().checkErrorShortPassword();
    }

    @CaseId(444)
    @Story("Тест неуспешной авторизации с неверным паролем")
    @Test(description = "Тест неуспешной авторизации с неверным паролем", groups = "regression")
    public void noAuthWithWrongPassword() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultUser().getEmail());
        login().setPassword("wrongpassword");
        login().submit();
        login().checkErrorInvalidEmailOrPassword();
    }

    @CaseId(415)
    @Story("Тест успешной авторизации")
    @Test(description = "Тест успешной авторизации", groups = {"regression", "smoke"})
    public void successAuthOnAdminLoginPage() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultAdmin().getEmail());
        login().setPassword(UserManager.getDefaultAdmin().getPassword());
        login().submit();

        main().interactAuthoredHeader().checkAdminAuth();
    }

    @CaseId(2)
    @Story("Тест логаута из админки")
    @Test(description = "Тест логаута из админки", groups = {"regression", "smoke"})
    public void successLogoutFromAdminPage() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultAdmin().getEmail());
        login().setPassword(UserManager.getDefaultAdmin().getPassword());
        login().submit();

        main().interactAuthoredHeader().clickToProfileMenu();
        main().interactAuthoredHeader().clickToLogout();

        login().checkTitle();
    }

    @CaseId(417)
    @Story("Тест недоступности админки пользователю без админ. прав")
    @Test(description = "Тест недоступности админки пользователю без админ. прав", groups = {"regression", "smoke"})
    public void loginWithoutAdminPermission() {
        login().goToPage();
        login().setUsername(UserManager.userWithoutAdminPermission().getEmail());
        login().setPassword(UserManager.userWithoutAdminPermission().getPassword());
        login().submit();
        main().interactAuthoredHeader().checkIsNotAuth();
    }
}
