package ru.instamart.tests.administration;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.application.Users;
import ru.instamart.application.platform.modules.Administration;
import ru.instamart.application.platform.modules.User;
import ru.instamart.application.Elements;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.models.UserData;
import ru.instamart.testdata.generate;
import ru.instamart.tests.TestBase;

import static ru.instamart.application.Config.TestsConfiguration.AdministrationTests.enableUsersSectionTests;

public class AdministrationUsersSectionTests extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = enableUsersSectionTests,
            description = "Тест поиска пользователя в админке",
            groups = {"acceptance","regression"},
            priority = 10701
    )
    public void successSearchUser() {
        Administration.Users.searchUser(Users.superuser());

        Assert.assertEquals(
                kraken.grab().text(Elements.Administration.UsersSection.firstUserLogin()), Users.superuser().getEmail(),
                    "Не работает поиск пользователя в админке");
    }

    @Test(  enabled = enableUsersSectionTests,
            description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"regression"},
            priority = 10702
    )
    public void successGrantAndRevokeAdminPrivileges() {
        SoftAssert softAssert = new SoftAssert();

        User.Do.quickLogout();
        UserData testuser = generate.testCredentials("admin");
        User.Do.registration(testuser);

        Administration.Users.grantAdminPrivileges(testuser);
        User.Do.quickLogout();

        User.Do.login(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertTrue(
                kraken.detect().isInAdmin(),
                    "Пользователю не предоставляются админские права");

        Administration.Users.revokeAdminPrivileges(testuser);
        User.Do.quickLogout();

        User.Do.login(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertFalse(
                kraken.detect().isInAdmin(),
                    "У пользователя не снимаются админские права");

        User.Do.quickLogout();
        softAssert.assertAll();
    }

    @Test(  enabled = enableUsersSectionTests,
            description = "Тест смены пароля пользователю",
            groups = {"regression"},
            priority = 10703
    )
    public void successChangePassword() {
        User.Do.quickLogout();
        UserData testuser = generate.testCredentials("user");
        User.Do.registration();

        Administration.Users.editUser(testuser);
        Administration.Users.changePassword("654321");
        User.Do.quickLogout();

        User.Do.login(testuser.getEmail(), "654321");

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не удалось авторизоваться пользователем после смены пароля через админку");

        User.Do.quickLogout();
    }

    @Test(  enabled = enableUsersSectionTests,
            description = "Тест проставления пользователю флага B2B",
            groups = {"regression"},
            priority = 10704
    )
    public void successGrantB2BStatus() {
        User.Do.quickLogout();
        UserData testuser = generate.testCredentials("user");
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();
        kraken.perform().refresh();

        Assert.assertTrue(
                kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox()),
                    "Пользователю не проставляется флаг B2B");
    }

    @Test(  enabled = enableUsersSectionTests,
            description = "Тест поиска B2B пользователя в админке",
            groups = {"regression"},
            priority = 10705
    )
    public void successSearchB2BUser() {
        SoftAssert softAssert = new SoftAssert();
        User.Do.quickLogout();
        UserData testuser = generate.testCredentials("user");
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();

        Administration.Users.searchUser(testuser, true,false);

        softAssert.assertEquals(
                kraken.grab().text(Elements.Administration.UsersSection.firstUserLogin()), testuser.getEmail(),
                    "Не работает поиск B2B пользователя в админке");

        softAssert.assertTrue(
                kraken.detect().isElementDisplayed(Elements.Administration.UsersSection.firstUserB2BLabel()),
                    "У пользователя не отображается B2B метка");

        softAssert.assertAll();
    }

    @Test(  enabled = enableUsersSectionTests,
            description = "Тест снятия B2B флага у пользователя",
            groups = {"regression"},
            priority = 10706
    )
    public void successRevokeB2BStatus() {
        SoftAssert softAssert = new SoftAssert();
        User.Do.quickLogout();
        UserData testuser = generate.testCredentials("user");
        User.Do.registration(testuser);

        Administration.Users.editUser(testuser);
        Administration.Users.grantB2B();
        User.Do.quickLogout();

        User.Do.login(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        Administration.Users.editUser(testuser);
        Administration.Users.revokeB2B();

        Administration.Users.searchUser(testuser , true,false);

        softAssert.assertFalse(
                kraken.detect().isElementPresent(Elements.Administration.UsersSection.firstUserLogin()),
                    "Пользователь находится как B2B после снятия флага");

        Administration.Orders.searchOrder(number, true);

        softAssert.assertEquals(
                kraken.grab().text(Elements.Administration.ShipmentsSection.firstOrderNumberInTable()), number,
                    "Не работает поиск старого B2B заказа после снятия B2B флага у пользователя");

        Administration.Orders.cancelOrder(number);
        softAssert.assertAll();
    }
}
