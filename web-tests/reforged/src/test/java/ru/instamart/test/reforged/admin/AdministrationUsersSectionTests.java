package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.reforged.core.service.KrakenDriver;
import ru.instamart.test.reforged.BaseTest;

import static ru.instamart.reforged.admin.AdminRout.*;


@Epic("Админка STF")
@Feature("Управление юзерами")
public class AdministrationUsersSectionTests extends BaseTest {

    final ApiHelper helper = new ApiHelper();

    @CaseId(31)
    @Story("Тест поиска пользователя в админке")
    @Test(description = "Тест поиска пользователя в админке",
            groups = {"sbermarket-acceptance", "sbermarket-regression", ""}
    )
    public void successSearchUser() {

        String email = UserManager.getDefaultAdmin().getLogin();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByEmail(email);
        users().clickToSubmit();
        users().checkFoundUserEmail(users().getFoundUserEmail(), email);
    }

    @CaseId(32)
    @Story("Тест предоставления и отзыва админских прав пользователю")
    @Test(description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {""}
    )
    public void successGrantAndRevokeAdmin() {

        final UserData userData = UserManager.getUser();
        String email = Generate.emailAdmin();
        String password = userData.getPassword();
        String phoneNumber = userData.getPhone();
        helper.auth(userData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(phoneNumber);
        users().clickToSubmit();
        users().clickToEditUser();
        users().clearUserEmail();
        users().fillUserEmail(email);
        users().fillPassword(password);
        users().fillPasswordConfirmation(password);
        users().checkAdminRole();
        users().clickToSaveUserProfileChanges();
        main().doLogout();

        login().goToPage();
        login().setUsername(email);
        login().setPassword(password);
        login().submit();

        main().checkAuth();
        main().doLogout();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(phoneNumber);
        users().clickToSubmit();
        users().clickToEditUser();
        users().checkAdminRole();
        users().clickToSaveUserProfileChanges();
        main().doLogout();

        login().goToPage();
        login().setUsername(email);
        login().setPassword(password);
        login().submit();

        main().checkIsNotAuth();
    }

    @CaseId(33)
    @Story("Тест смены email пользователя")
    @Test(description = "Тест смены email пользователя",
            groups = {"sbermarket-regression", ""}
    )
    public void successChangeEmail() {

        final UserData userData = UserManager.getUser();
        String email = Generate.email();
        String phoneNumber = userData.getPhone();
        helper.auth(userData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(phoneNumber);
        users().clickToSubmit();
        users().clickToEditUser();
        users().clearUserEmail();
        users().fillUserEmail(email);

        users().clickToSaveUserProfileChanges();
        users().checkEditUserEmail(users().getEditUserEmail(), email);
    }

    @CaseId(34)
    @Story("Тест проставления пользователю флага B2B")
    @Test(description = "Тест проставления пользователю флага B2B",
            groups = {"sbermarket-regression", ""}
    )
    //TODO в основном тесте есть еще проверка невозможности поиска заказа b2b. Лучше вынести в отдельный тест в shipments
    public void successGrantAndRevokeB2BStatus() {

        final UserData userData = UserManager.getUser();
        String phoneNumber = userData.getPhone();
        helper.auth(userData);

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByPhoneNumber(phoneNumber);
        users().clickToSubmit();
        users().clickToEditUser();
        users().setB2BUser();
        users().clickToSaveUserProfileChanges();
        KrakenDriver.refresh();
        users().checkB2BIsSelected();

        users().unsetB2BUser();
        users().clickToSaveUserProfileChanges();
        KrakenDriver.refresh();

        users().checkB2BIsNotSelected();
    }
}
