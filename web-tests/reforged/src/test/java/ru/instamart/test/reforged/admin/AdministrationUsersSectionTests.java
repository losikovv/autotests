package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.login;
import static ru.instamart.reforged.admin.AdminRout.users;
import static ru.instamart.reforged.core.service.KrakenDriver.closeWebDriver;
import static ru.instamart.reforged.stf.page.StfRouter.home;
import static ru.instamart.reforged.stf.page.StfRouter.shop;


@Epic("Админка STF")
@Feature("Управление юзерами")
public class AdministrationUsersSectionTests extends BaseTest {

    @CaseId(31)
    @Story("Тест поиска пользователя в админке")
    @Test(description = "Тест поиска пользователя в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", "admin-ui-smoke"}
    )
    public void successSearchUser() {
        String testEmail = UserManager.getDefaultAdmin().getLogin();
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
        users().goToPage();
        users().fillSearchByEmail(testEmail);
        users().clickOnSubmit();
        users().checkFoundUserEmail(users().getFoundUserEmail(), testEmail);
    }

    @CaseId(33)
    @Story("Тест смены email пользователя")
    @Test(description = "Тест смены email пользователя",
            groups = {"sbermarket-regression", "admin-ui-smoke"}
    )
    public void successChangeEmail() {
        String email = Generate.emailAdmin();
        String phone = Generate.phoneNumber();
        //сделать отдельного юзера, который будет только для этого теста?
        home().goToPage();
        home().openLoginModal();
        home().interactAuthModal().fillPhone(phone);
        home().interactAuthModal().sendSms();
        home().interactAuthModal().fillSMS(Config.DEFAULT_SMS);
        //хочу удалить строки ниже. Не может ли быть случая, что не успеет создастся пользователь?
        shop().interactHeader().clickToProfile();
        shop().interactHeader().interactAccountMenu().clickToLogout();
        closeWebDriver();
        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());
        users().goToPage();
        users().fillSearchByPhoneNumber(phone);
        users().clickOnSubmit();
        users().clickToEditUser();
        users().clearUserEmail();
        users().fillUserEmail(email);
        users().clickToSaveChanges();
        users().checkEditUserEmail(users().getEditUserEmail(), email);
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
