package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserManager;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.main;

@Epic("Админка STF")
@Feature("Страница логина")
public final class AdministrationLoginTests {

    @CaseId(439)
    @Story("Тест валидации элементов логин-страницы админки")
    @Test(description = "Тест валидации элементов логин-страницы админки",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successValidateAdministrationLoginPage() {

    }

    @CaseId(440)
    @Story("Тест неуспешной авторизации с пустыми полями")
    @Test(description = "Тест неуспешной авторизации с пустыми полями",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void noAuthWithEmptyFields() {

    }

    @CaseId(441)
    @Story("Тест неуспешной авторизации с некорректным логином")
    @Test(description = "Тест неуспешной авторизации с некорректным логином",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void noAuthWithIncorrectUsername() {

    }

    @CaseId(442)
    @Story("Тест неуспешной авторизации с несуществующим логином")
    @Test(description = "Тест неуспешной авторизации с несуществующим логином",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void noAuthWithNonexistingUser() {

    }

    @CaseId(443)
    @Story("Тест неуспешной авторизации с коротким паролем")
    @Test(description = "Тест неуспешной авторизации с коротким паролем",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void noAuthWithShortPassword() {

    }

    @CaseId(444)
    @Story("Тест неуспешной авторизации с неверным паролем")
    @Test(description = "Тест неуспешной авторизации с неверным паролем",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void noAuthWithWrongPassword() {

    }

    @CaseId(415)
    @Story("Тест успешной авторизации")
    @Test(description = "Тест успешной авторизации",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successAuthOnAdminLoginPage() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultAdmin().getLogin());
        login().setPassword(UserManager.getDefaultAdmin().getPassword());
        login().submit();
        main().checkAuth();
    }

    @CaseId(2)
    @Story("Тест логаута из админки")
    @Test(description = "Тест логаута из админки",
            groups = {"admin-ui-smoke"})
    public void successLogoutFromAdminPage() {
        login().goToPage();
        login().setUsername(UserManager.getDefaultAdmin().getLogin());
        login().setPassword(UserManager.getDefaultAdmin().getPassword());
        login().submit();
        main().clickToProfileMenu();
        main().clickToLogout();
    }
}
