package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.users;


@Epic("Админка STF")
@Feature("Управление юзерами")
public class AdministrationUsersSectionTests extends BaseTest {

    @CaseId(31)
    @Story("Тест поиска пользователя в админке")
    @Test(description = "Тест поиска пользователя в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successSearchUser() {
        String testEmail = "autotestuser@instamart.ru";
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
        users().goToPage();
        users().fillEmail(testEmail);
        users().clickOnSubmit();
        users().checkFoundUserEmail(users().getFoundUserEmail(), testEmail);
    }

    @Issue(value = "STF-7163")
    @CaseId(33)
    @Story("Тест смены email пользователя")
    @Test(description = "Тест смены email пользователя",
            groups = {"sbermarket-regression", "admin-ui-smoke"}
    )
    public void successChangeEmail() {

    }

    @CaseId(34)
    @Issue(value = "STF-7163")
    @Story("Тест проставления пользователю флага B2B")
    @Test(description = "Тест проставления пользователю флага B2B",
            groups = {"sbermarket-regression", "admin-ui-smoke"}
    )
    public void successGrantB2BStatus() {

    }

    @Story("Тест снятия B2B флага у пользователя")
    @Test(description = "Тест снятия B2B флага у пользователя",
            groups = {"sbermarket-regression"}
    )
    public void successRevokeB2BStatus() {

    }
}
