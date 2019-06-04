package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.enableAdministrationTests;

public class AdministrationUsers extends TestBase {

    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() {
        kraken.reach().admin();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест поиска пользователя в админке",
            groups = {"acceptance","regression"},
            priority = 2201
    )
    public void successSearchUser() throws Exception {
        kraken.admin().searchUser(Users.superuser());

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Users.firstUserLogin()), Users.superuser().getEmail(),
                "Не работает поиск пользователя в админке");
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"regression"},
            priority = 2203
    )
    public void successGrantAndRevokeAdminPrivileges() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("admin");
        kraken.perform().registration(testuser);

        kraken.admin().grantAdminPrivileges(testuser);
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertTrue(kraken.detect().isInAdmin(),
                "Пользователю не предоставляются админские права");

        kraken.admin().revokeAdminPrivileges(testuser);
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.get().page(Pages.Admin.shipments());

        softAssert.assertFalse(kraken.detect().isInAdmin(),
                "У пользователя не снимаются админские права");

        kraken.perform().quickLogout();

        softAssert.assertAll();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест смены пароля пользователю",
            groups = {"regression"},
            priority = 2204
    )
    public void successChangePassword() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration();

        kraken.admin().editUser(testuser);
        kraken.admin().changePassword("654321");
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser.getEmail(), "654321");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не удалось авторизоваться пользователем после смены пароля через админку");

        kraken.perform().quickLogout();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест проставления пользователю флага B2B",
            groups = {"regression"},
            priority = 2205
    )
    public void successGrantB2BStatus() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        kraken.admin().editUser(testuser);
        kraken.admin().grantB2B();
        kraken.perform().refresh();

        Assert.assertTrue(kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.b2bCheckbox()),
                "Пользователю не проставляется флаг B2B");
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест поиска B2B пользователя в админке",
            groups = {"regression"},
            priority = 2206
    )
    public void successSearchB2BUser() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        kraken.admin().editUser(testuser);
        kraken.admin().grantB2B();

        kraken.admin().searchUser(testuser, true,false);

        softAssert.assertEquals(kraken.grab().text(Elements.Admin.Users.firstUserLogin()), testuser.getEmail(),
                "Не работает поиск B2B пользователя в админке");

        softAssert.assertTrue(kraken.detect().isElementDisplayed(Elements.Admin.Users.firstUserB2BLabel()),
                "У пользователя не отображается B2B метка");

        softAssert.assertAll();
    }

    @Test(  enabled = enableAdministrationTests,
            description = "Тест снятия B2B флага у пользователя",
            groups = {"regression"},
            priority = 2207
    )
    public void successRevokeB2BStatus() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        kraken.admin().editUser(testuser);
        kraken.admin().grantB2B();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        kraken.admin().editUser(testuser);
        kraken.admin().revokeB2B();

        kraken.admin().searchUser(testuser , true,false);

        softAssert.assertFalse(kraken.detect().isElementPresent(Elements.Admin.Users.firstUserLogin()),
                "Пользователь находится как B2B после снятия флага");

        kraken.admin().searchOrder(number, true);

        softAssert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                "Не работает поиск старого B2B заказа после снятия B2B флага у пользователя");

        kraken.admin().cancelOrder(number);
        softAssert.assertAll();
    }
}
