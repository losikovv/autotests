package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление юзерами")
public class AdministrationUsersSectionTests extends BaseTest {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(31)
    @Story("Тест поиска пользователя в админке")
    @Test(description = "Тест поиска пользователя в админке", groups = {"acceptance", "regression"})
    public void successSearchUser() {
        final String email = UserManager.getDefaultAdmin().getEmail();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByEmail(email);
        users().clickToSearch();
        users().checkFoundUserEmail(users().getFoundUserEmail(), email);
    }

    @CaseId(32)
    @Story("Тест предоставления и отзыва админских прав пользователю")
    @Test(description = "Тест предоставления и отзыва админских прав пользователю", groups = {"acceptance", "regression"})
    public void successGrantAndRevokeAdmin() {
        final UserData userData = UserManager.getQaUser();
        final String email = Generate.emailAdmin();
        final String password = userData.getPassword();
        final String phoneNumber = userData.getPhone();
        helper.auth(userData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(phoneNumber);
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().clearUserEmail();
        usersEdit().fillUserEmail(email);
        usersEdit().fillPassword(password);
        usersEdit().fillPasswordConfirmation(password);
        usersEdit().checkAdminRole();
        usersEdit().clickToSave();
        main().doLogout();

        login().goToPage();
        login().setUsername(email);
        login().setPassword(password);
        login().submit();

        main().interactAuthoredHeader().checkAdminAuth();
        main().doLogout();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(phoneNumber);
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().uncheckAdminRole();
        usersEdit().clickToSave();
        main().doLogout();

        login().goToPage();
        login().setUsername(email);
        login().setPassword(password);
        login().submit();

        main().interactAuthoredHeader().checkIsNotAuth();
    }

    @CaseId(33)
    @Story("Тест смены email пользователя")
    @Test(description = "Тест смены email пользователя", groups = {"acceptance", "regression"})
    public void successChangeEmail() {
        final UserData userData = UserManager.getQaUser();
        final String email = Generate.email();
        helper.auth(userData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().clearUserEmail();
        usersEdit().fillUserEmail(email);

        usersEdit().clickToSave();
        usersEdit().checkEditUserEmail(usersEdit().getEditUserEmail(), email);
    }

    @CaseId(34)
    @Story("Тест проставления пользователю флага B2B")
    @Test(description = "Тест проставления пользователю флага B2B", groups = {"acceptance", "regression"})
    //TODO в основном тесте есть еще проверка невозможности поиска заказа b2b. Лучше вынести в отдельный тест в shipments
    public void successGrantAndRevokeB2BStatus() {
        final UserData userData = UserManager.getQaUser();
        helper.auth(userData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().setB2BUser();
        usersEdit().clickToSave();
        usersEdit().refresh();
        usersEdit().checkB2BIsSelected();

        usersEdit().unsetB2BUser();
        usersEdit().clickToSave();
        usersEdit().refresh();

        usersEdit().checkB2BIsNotSelected();
    }
}
