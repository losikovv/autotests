package ru.instamart.test.reforged.admin;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.helper.ApiHelper;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.PaymentCards;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.reforged.core.config.UiProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.reforged.admin.AdminRout.*;

@Epic("Админка STF")
@Feature("Управление юзерами")
public final class AdministrationUsersSectionTests {

    private final ApiHelper helper = new ApiHelper();

    @CaseId(19)
    @Story("Тест поиска пользователя в админке")
    @Test(description = "Работоспособность поиска в списке юзеров", groups = {"regression", "production"})
    public void successSearchUser() {
        final String email = UserManager.getDefaultAdmin().getEmail();

        login().goToPage();
        login().auth(UserManager.getDefaultAdminAllRoles());

        users().goToPage();
        users().fillSearchByEmailOrPhone(email);
        users().clickToSearch();
        users().checkFoundUserEmail(users().getFoundUserEmail(), email);
    }

    @CaseId(32)
    @Story("Тест предоставления и отзыва админских прав пользователю")
    @Test(description = "Тест предоставления и отзыва админских прав пользователю", groups = "regression")
    public void successGrantAndRevokeAdmin() {
        final UserData userData = UserManager.getQaUser();
        final String password = Generate.literalString(8);

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

    @CaseId(34)
    @Story("Тест проставления пользователю флага B2B")
    @Test(description = "Тест проставления пользователю флага B2B", groups = "regression")
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

    @CaseId(508)
    @Test(description = "Отвязка платежных карт", groups = "regression")
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

    @CaseId(509)
    @Test(description = "Отвязка номера телефона от аккаунта", groups = "regression")
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
