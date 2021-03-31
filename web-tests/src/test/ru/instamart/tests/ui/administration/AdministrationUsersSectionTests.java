package ru.instamart.tests.ui.administration;

import instamart.core.listeners.ExecutionListenerImpl;
import instamart.core.settings.Config;
import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.AdminPageCheckpoints;
import instamart.ui.checkpoints.users.AdminSearchUsersCheckpoints;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Administration;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

@Epic("Админка STF")
@Feature("Управление юзерами")
@Listeners(ExecutionListenerImpl.class)
public class AdministrationUsersSectionTests extends TestBase {
    private static String phone;
    private static String email;
    AdminSearchUsersCheckpoints searchChecks = new AdminSearchUsersCheckpoints();
    AdminPageCheckpoints adminChecks = new AdminPageCheckpoints();
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        phone = Generate.phoneNumber();
        email = Generate.emailAdmin();
    }

    @CaseId(31)
    @Story("Тест поиска пользователя в админке")
    @Test(  description = "Тест поиска пользователя в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successSearchUser() {
        User.Logout.quicklyAdmin();
        Administration.Users.searchUser(UserManager.getDefaultAdmin());
        searchChecks.checkSearchWorks();
    }

    //TODO тест удаления юзера
    @Issue(value = "STF-7163")
    @Issue(value = "STF-7220")
    @CaseId(32)
    @Story("Тест предоставления и отзыва админских прав пользователю")
    @Test(  description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"sbermarket-regression","admin-ui-smoke"}
    )
    public void successGrantAndRevokeAdminPrivileges() {
        User.Logout.quickly();
        String role= "superadmin";
        Shop.AuthModal.openAuthLending();
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.changeEmail(email);
        Administration.Users.changePassword(phone);
        Administration.Users.grantAdminPrivileges();
        //User.Logout.quicklyAdmin();
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        kraken.get().adminPage("");
        kraken.reach().admin(email,phone,role);
        adminChecks.checkIsAdminPageOpen();
        //User.Logout.quicklyAdmin();
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        kraken.get().adminPage("");
        Administration.Users.revokeAdminPrivileges(phone);
        //User.Logout.quicklyAdmin();
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        kraken.get().adminPage("");
        kraken.reach().admin(email,phone,"superuser");
        adminChecks.checkIsNotAdminPageOpen();
    }

    @Issue(value = "STF-7163")
    @CaseId(33)
    @Story("Тест смены email пользователя")
    @Test(  description = "Тест смены email пользователя",
            groups = {"sbermarket-regression","admin-ui-smoke"}
    )
    public void successChangeEmail() {
        User.Logout.quickly();
        Shop.AuthModal.openAuthLending();
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.changeEmail(email);
        Administration.AdminNavigation.switchTotab("Пользователи");
        Administration.Users.editUser(email);
        baseChecks.checkValueIsCorrectInElement(Elements.Administration.UsersSection.UserPage.emailField(),
                email,"почта выбранного пользователя не соответсвует ожидаемому значению: "+email);
    }

    @CaseId(34)
    @Issue(value = "STF-7163")
    @Story("Тест проставления пользователю флага B2B")
    @Test(  description = "Тест проставления пользователю флага B2B",
            groups = {"sbermarket-regression","admin-ui-smoke"}
    )
    public void successGrantB2BStatus() {
        User.Logout.quickly();
        Shop.AuthModal.openAuthLending();
        User.Do.registration(phone);
        User.Do.sendSms(Config.DEFAULT_SMS);
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.grantB2B();
        kraken.perform().refresh();
        baseChecks.checkCheckboxIsSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
    }

    @Story("Тест снятия B2B флага у пользователя")
    @Test(  description = "Тест снятия B2B флага у пользователя",
            groups = {"sbermarket-regression"}
    )
    public void successRevokeB2BStatus() {
        SoftAssert softAssert = new SoftAssert();
        UserData testuser = UserManager.getUser();
//        User.Do.registration(testuser);
        phone = Generate.phoneNumber();
        User.Do.registration(
                phone,
                "111111"
        );
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.grantB2B();
        //User.Logout.quickly();
        kraken.getWebDriver().close(); //Это нужно удалить, после того как починят багу
        kraken.get().baseUrl();
        User.Auth.withEmail(testuser);
        kraken.perform().order();
        String number = kraken.grab().shipmentNumber();

        Administration.Users.editUser(testuser);
        Administration.Users.revokeB2B();

        Administration.Users.searchUser(testuser);

        softAssert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.UsersSection.userEmail()),
                    "Пользователь находится как B2B после снятия флага");

        Administration.Orders.searchOrder(number,true);

        softAssert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber()), number,
                    "Не работает поиск старого B2B заказа после снятия B2B флага у пользователя");

        Administration.Orders.cancelOrder(number);
        softAssert.assertAll();
    }
}
