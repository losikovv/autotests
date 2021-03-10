package ru.instamart.tests.ui.administration;

import instamart.core.testdata.UserManager;
import instamart.core.testdata.ui.Generate;
import instamart.ui.checkpoints.BaseUICheckpoints;
import instamart.ui.checkpoints.users.AdminPageCheckpoints;
import instamart.ui.checkpoints.users.AdminSearchUsersCheckpoints;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Administration;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Issue;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.ui.TestBase;

public class AdministrationUsersSectionTests extends TestBase {
    private static String phone;
    private static String email;
    AdminSearchUsersCheckpoints searchChecks = new AdminSearchUsersCheckpoints();
    AdminPageCheckpoints adminChecks = new AdminPageCheckpoints();
    BaseUICheckpoints baseChecks = new BaseUICheckpoints();
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        User.Logout.quickly();
        phone = Generate.phoneNumber();
        email = Generate.emailAdmin();
    }

    @CaseId(31)
    @Test(  description = "Тест поиска пользователя в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression","admin-ui-smoke"}
    )
    public void successSearchUser() {
        kraken.reach().admin();
        Administration.Users.searchUser(UserManager.getDefaultAdmin());
        searchChecks.checkSearchWorks();
    }

    //TODO тест удаления юзера
    @Issue(value = "STF-7163")
    @Issue(value = "STF-7220")
    @CaseId(32)
    @Test(  description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"sbermarket-regression","admin-ui-smoke"}
    )
    public void successGrantAndRevokeAdminPrivileges() {
        String role= "superadmin";
        User.Do.registration(
                phone,
                "111111"
        );
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.changeEmail(email);
        Administration.Users.changePassword(phone);
        Administration.Users.grantAdminPrivileges();
        User.Logout.quickly();
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
        kraken.reach().admin(email,phone,role);
        adminChecks.checkIsAdminPageOpen();
        User.Logout.quickly();
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
        Administration.Users.revokeAdminPrivileges(phone);
        User.Logout.quickly();
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
        kraken.reach().admin(email,phone,"superuser");
        adminChecks.checkIsNotAdminPageOpen();
    }

    @Issue(value = "STF-7163")
    @CaseId(33)
    @Test(  description = "Тест смены email пользователя",
            groups = {"sbermarket-regression","admin-ui-smoke"}
    )
    public void successChangeEmail() {
        User.Do.registration(
                phone,
                "111111"
        );
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.changeEmail(email);
        Administration.AdminNavigation.switchTotab("Пользователи");
        Administration.Users.editUser(email);
        baseChecks.checkValueIsCorrectInElement(Elements.Administration.UsersSection.UserPage.emailField(),
                email,"почта выбранного пользователя не соответсвует ожидаемому значению: "+email);
    }

    @CaseId(34)
    @Issue(value = "STF-7163")
    @Test(  description = "Тест проставления пользователю флага B2B",
            groups = {"sbermarket-regression","admin-ui-smoke"}
    )
    public void successGrantB2BStatus() {
        User.Do.registration(
                phone,
                "111111"
        );
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.grantB2B();
        kraken.perform().refresh();
        baseChecks.checkCheckboxIsSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
    }

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
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
        Administration.Users.editUser(phone);
        Administration.Users.grantB2B();
        User.Logout.quickly();
        kraken.getWebDriver().manage().deleteAllCookies();//Это нужно удалить, после того как починят багу
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
