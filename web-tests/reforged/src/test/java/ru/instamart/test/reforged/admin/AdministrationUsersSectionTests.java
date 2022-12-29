package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;

import static ru.instamart.reforged.Group.REGRESSION_ADMIN;
import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление юзерами")
public final class AdministrationUsersSectionTests {

    private final ApiHelper helper = new ApiHelper();

    @TmsLink("19")
    @Story("Тест поиска пользователя в админке")
    @Test(description = "Работоспособность поиска в списке юзеров", groups = REGRESSION_ADMIN)
    public void successSearchUser() {
        final var email = UserManager.getDefaultAdmin().getEmail();

        login().goToPage();
        login().auth(UserManager.getDefaultAdmin());

        users().goToPage();
        users().fillSearchByEmailOrPhone(email);
        users().clickToSearch();
        users().checkFoundUserEmail(users().getFoundUserEmail(), email);
    }

    @TmsLink("32")
    @Story("Тест предоставления и отзыва админских прав пользователю")
    @Test(description = "Тест предоставления и отзыва админских прав пользователю", groups = REGRESSION_ADMIN)
    public void successGrantAndRevokeAdmin() {
        final UserData userData = UserManager.getQaUser();
        final String password = Generate.generatePassword(12, true, true, true, true);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().fillPassword(password);
        usersEdit().fillPasswordConfirmation(password);
        usersEdit().clickToSaveUserPassword();
        usersEdit().interactFlashAlert().checkSuccessFlashVisible();
        usersEdit().interactFlashAlert().checkSuccessFlashNotVisible();

        usersEdit().checkAdminRole();
        usersEdit().clickToSaveUserRole();
        usersEdit().interactFlashAlert().checkSuccessFlashVisible();
        usersEdit().interactFlashAlert().checkSuccessFlashNotVisible();

        usersEdit().interactAuthoredHeader().clickToProfileMenu();
        usersEdit().interactAuthoredHeader().clickToLogout();

        login().setUsername(userData.getEmail());
        login().setPassword(password);
        login().submit();

        main().interactAuthoredHeader().checkAdminAuth();

        usersEdit().interactAuthoredHeader().clickToProfileMenu();
        usersEdit().interactAuthoredHeader().clickToLogout();

        login().auth(UserManager.getDefaultAdminAllRoles());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().uncheckAdminRole();
        usersEdit().clickToSaveUserRole();
        usersEdit().interactFlashAlert().checkSuccessFlashVisible();
        usersEdit().interactFlashAlert().checkSuccessFlashNotVisible();

        usersEdit().interactAuthoredHeader().clickToProfileMenu();
        usersEdit().interactAuthoredHeader().clickToLogout();

        login().setUsername(userData.getEmail());
        login().setPassword(password);
        login().submit();

        login().checkErrorInvalidEmailOrPassword();
    }

    @TmsLink("34")
    @Story("Тест проставления пользователю флага B2B")
    @Test(description = "Тест проставления пользователю флага B2B", groups = REGRESSION_ADMIN)
    //TODO в основном тесте есть еще проверка невозможности поиска заказа b2b. Лучше вынести в отдельный тест в shipments
    public void successGrantAndRevokeB2BStatus() {
        final UserData userData = UserManager.getQaUser();

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().setB2BUser();
        usersEdit().clickToSaveUserB2B();
        usersEdit().interactFlashAlert().checkSuccessFlashVisible();
        usersEdit().interactFlashAlert().checkSuccessFlashNotVisible();
        usersEdit().checkB2BIsSelected();

        usersEdit().unsetB2BUser();
        usersEdit().clickToSaveUserB2B();
        usersEdit().interactFlashAlert().checkSuccessFlashVisible();
        usersEdit().interactFlashAlert().checkSuccessFlashNotVisible();
        usersEdit().refresh();

        usersEdit().checkB2BIsNotSelected();
    }

    @TmsLink("508")
    @Test(description = "Отвязка платежных карт", groups = REGRESSION_ADMIN)
    public void testBlockPaymentCards() {
        final var userData = UserManager.getQaUser();
        var card = PaymentCards.testCardNo3dsWithSpasibo();
        helper.bindCardToUser(userData, UiProperties.DEFAULT_SID,
                card);

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().clickToBlockAllCards();
        usersEdit().interactFlashAlert().checkSuccessFlashVisible();
        usersEdit().interactFlashAlert().checkSuccessFlashNotVisible();
    }

    @TmsLink("509")
    @Test(description = "Отвязка номера телефона от аккаунта", groups = REGRESSION_ADMIN)
    public void testBlockPhone() {
        final var userData = UserManager.getQaUser();
        helper.setAddress(userData, RestAddresses.Moscow.defaultAddress());

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        users().goToPage();
        users().fillSearchByEmailOrPhone(userData.getPhone());
        users().clickToSearch();
        users().clickToEditUser();

        usersEdit().clickToBlockPhone();
        usersEdit().interactFlashAlert().checkSuccessFlashVisible();
        usersEdit().interactFlashAlert().checkSuccessFlashNotVisible();
    }
}
