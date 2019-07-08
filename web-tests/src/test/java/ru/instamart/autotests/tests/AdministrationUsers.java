package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.appmanager.AdministrationHelper;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.testAdministration;

public class AdministrationUsers extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = testAdministration,
            description = "Тест поиска пользователя в админке",
            groups = {"acceptance","regression"},
            priority = 2701
    )
    public void successSearchUser() {
        AdministrationHelper.Users.searchUser(Users.superuser());

        Assert.assertEquals(
                kraken.grab().text(Elements.Admin.Users.firstUserLogin()), Users.superuser().getEmail(),
                    "Не работает поиск пользователя в админке");
    }

    @Test(  enabled = testAdministration,
            description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"regression"},
            priority = 2702
    )
    public void successGrantAndRevokeAdminPrivileges() {
        SoftAssert softAssert = new SoftAssert();

        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("admin");
        kraken.perform().registration(testuser);

        AdministrationHelper.Users.grantAdminPrivileges(testuser);
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertTrue(
                kraken.detect().isInAdmin(),
                    "Пользователю не предоставляются админские права");

        AdministrationHelper.Users.revokeAdminPrivileges(testuser);
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertFalse(
                kraken.detect().isInAdmin(),
                    "У пользователя не снимаются админские права");

        kraken.perform().quickLogout();
        softAssert.assertAll();
    }

    @Test(  enabled = testAdministration,
            description = "Тест смены пароля пользователю",
            groups = {"regression"},
            priority = 2703
    )
    public void successChangePassword() {
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration();

        AdministrationHelper.Users.editUser(testuser);
        AdministrationHelper.Users.changePassword("654321");
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser.getEmail(), "654321");

        Assert.assertTrue(
                kraken.detect().isUserAuthorised(),
                    "Не удалось авторизоваться пользователем после смены пароля через админку");

        kraken.perform().quickLogout();
    }

    @Test(  enabled = testAdministration,
            description = "Тест проставления пользователю флага B2B",
            groups = {"regression"},
            priority = 2704
    )
    public void successGrantB2BStatus() {
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        AdministrationHelper.Users.editUser(testuser);
        AdministrationHelper.Users.grantB2B();
        kraken.perform().refresh();

        Assert.assertTrue(
                kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.b2bCheckbox()),
                    "Пользователю не проставляется флаг B2B");
    }

    @Test(  enabled = testAdministration,
            description = "Тест поиска B2B пользователя в админке",
            groups = {"regression"},
            priority = 2705
    )
    public void successSearchB2BUser() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        AdministrationHelper.Users.editUser(testuser);
        AdministrationHelper.Users.grantB2B();

        AdministrationHelper.Users.searchUser(testuser, true,false);

        softAssert.assertEquals(
                kraken.grab().text(Elements.Admin.Users.firstUserLogin()), testuser.getEmail(),
                    "Не работает поиск B2B пользователя в админке");

        softAssert.assertTrue(
                kraken.detect().isElementDisplayed(Elements.Admin.Users.firstUserB2BLabel()),
                    "У пользователя не отображается B2B метка");

        softAssert.assertAll();
    }

    @Test(  enabled = testAdministration,
            description = "Тест снятия B2B флага у пользователя",
            groups = {"regression"},
            priority = 2706
    )
    public void successRevokeB2BStatus() {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        AdministrationHelper.Users.editUser(testuser);
        AdministrationHelper.Users.grantB2B();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        AdministrationHelper.Users.editUser(testuser);
        AdministrationHelper.Users.revokeB2B();

        AdministrationHelper.Users.searchUser(testuser , true,false);

        softAssert.assertFalse(
                kraken.detect().isElementPresent(Elements.Admin.Users.firstUserLogin()),
                    "Пользователь находится как B2B после снятия флага");

        AdministrationHelper.Orders.searchOrder(number, true);

        softAssert.assertEquals(
                kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                    "Не работает поиск старого B2B заказа после снятия B2B флага у пользователя");

        AdministrationHelper.Orders.cancelOrder(number);
        softAssert.assertAll();
    }
}
