package ru.instamart.tests.administration;

import instamart.core.testdata.Users;
import instamart.core.testdata.ui.Generate;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.modules.Administration;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.tests.TestBase;

public class AdministrationUsersSectionTests extends TestBase {
    
    @BeforeMethod(alwaysRun = true,
            description ="Выполняем шаги предусловий для теста")
    public void beforeTest() {
        kraken.reach().admin();
    }

    @Test(  description = "Тест поиска пользователя в админке",
            groups = {"sbermarket-acceptance","sbermarket-regression"},
            priority = 10701
    )
    public void successSearchUser() {
        Administration.Users.searchUser(Users.superadmin());

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.UsersSection.userEmail()), Users.superadmin().getLogin(),
                    "Не работает поиск пользователя в админке");
    }

    @Test(  description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"sbermarket-regression"},
            priority = 10702
    )
    public void successGrantAndRevokeAdminPrivileges() {
        SoftAssert softAssert = new SoftAssert();

        User.Logout.quickly();
        UserData testuser = Generate.testCredentials("admin");
        User.Do.registration(testuser);

        Administration.Users.grantAdminPrivileges(testuser);
        User.Logout.quickly();

        User.Auth.withEmail(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertTrue(
                kraken.detect().isInAdmin(),
                    "Пользователю не предоставляются админские права");

        Administration.Users.revokeAdminPrivileges(testuser);
        User.Logout.quickly();

        User.Auth.withEmail(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertFalse(
                kraken.detect().isInAdmin(),
                    "У пользователя не снимаются админские права");

        User.Logout.quickly();
        softAssert.assertAll();
    }

    @Test(  description = "Тест смены пароля пользователю",
            groups = {"sbermarket-regression"},
            priority = 10703
    )
    public void successChangePassword() {
        User.Logout.quickly();
        UserData testuser = Generate.testCredentials("user");
        User.Do.registration();

        Administration.Users.editUser(testuser);
        Administration.Users.changePassword("654321");
        User.Logout.quickly();

        User.Auth.withEmail(testuser.getLogin(), "654321");

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не удалось авторизоваться пользователем после смены пароля через админку");

        User.Logout.quickly();
    }

    @Test(  description = "Тест проставления пользователю флага B2B",
            groups = {"sbermarket-regression"},
            priority = 10704
    )
    public void successGrantB2BStatus() {
        User.Logout.quickly();
        UserData testuser = Generate.testCredentials("user");
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();
        kraken.perform().refresh();

        Assert.assertTrue(
                kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox()),
                    "Пользователю не проставляется флаг B2B");
    }

    @Test(  description = "Тест снятия B2B флага у пользователя",
            groups = {"sbermarket-regression"},
            priority = 10706
    )
    public void successRevokeB2BStatus() {
        SoftAssert softAssert = new SoftAssert();
        User.Logout.quickly();
        UserData testuser = Generate.testCredentials("user");
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();
        User.Logout.quickly();

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
