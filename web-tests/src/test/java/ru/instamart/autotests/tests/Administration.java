package ru.instamart.autotests.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.Generate;


// Тесты админки


public class Administration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() throws Exception {
        kraken.perform().reachAdmin();
    }


    @Test(
            description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 700
    )
    public void noAccessAdministrationWithoutAdminPrivileges() throws Exception {
        kraken.perform().loginAs("user");

        assertPageIsUnavailable(Pages.Admin.shipments());

        kraken.perform().quickLogout();
    }


    @Test(
            description = "Тест возобновления заказа через админку",
            groups = {"acceptance","regression"},
            priority = 701
    )
    public void successResumeOrder() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.perform().order();
        String testOrder = kraken.grab().currentOrderNumber();
        kraken.perform().cancelLastOrder();
        kraken.get().adminOrderDetailsPage(testOrder);

        softAssert.assertTrue(kraken.detect().isOrderCanceled(),
                "Заказ уже активен\n");

        kraken.admin().resumeOrder();

        softAssert.assertFalse(kraken.detect().isOrderCanceled(),
                "Не возобновляется заказ через админку\n");

        kraken.perform().cancelLastOrder();
        softAssert.assertAll();
    }


    @Test(
            description = "Тест отмены заказа через админку",
            groups = {"acceptance","regression"},
            priority = 702
    )
    public void successCancelOrder() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().page("metro");
        kraken.perform().order();
        String testOrder = kraken.grab().currentOrderNumber();
        kraken.get().adminOrderDetailsPage(testOrder);

        softAssert.assertFalse(kraken.detect().isOrderCanceled(),
                "Заказ уже отменён\n");

        kraken.admin().cancelOrder();

        softAssert.assertTrue(kraken.detect().isOrderCanceled(),
                "Не отменяется заказ через админку\n");

        softAssert.assertAll();
    }


    @Test(
            description = "Тест шапки админки",
            groups = {"regression"},
            priority = 704

    )

    //Спрятать все лишнее

    public void successCheckHeader() throws Exception {

        orders();
        stores();
        products();
        imports();
        reports();
        settings();
        marketing();
        users();
        pages();

    }


    private void orders() throws Exception {

        kraken.go().adminOrders();
        assertPageIsAvailable();

        kraken.go().adminOrdersMulti();
        assertPageIsAvailable();

        kraken.go().adminOrdersVeeroute();
        assertPageIsAvailable();
    }

    private void stores() throws Exception {

        kraken.go().adminStores();
        assertPageIsAvailable();

        kraken.go().adminStoresRetailers();
        assertPageIsAvailable();

        kraken.go().adminStoresZones();
        assertPageIsAvailable();
    }

    private void products() throws Exception {

        kraken.go().adminProducts();
        assertPageIsAvailable();

        kraken.go().adminSubProducts();
        assertPageIsAvailable();

        kraken.go().adminProductsStats();
        assertPageIsAvailable();

        kraken.go().adminProductsOptionTypes();
        assertPageIsAvailable();

        kraken.go().adminProductsProperties();
        assertPageIsAvailable();

        kraken.go().adminProductsPrototypes();
        assertPageIsAvailable();

        kraken.go().adminProductsBrands();
        assertPageIsAvailable();

        kraken.go().adminProductsProducers();
        assertPageIsAvailable();

        kraken.go().adminProductsProducersCountries();
        assertPageIsAvailable();
    }

    private void imports() throws Exception {

        kraken.go().adminImport();
        assertPageIsAvailable();

        kraken.go().adminImportQueueOfTasks();
        assertPageIsAvailable();

        kraken.go().adminImportStats();
        assertPageIsAvailable();

        kraken.go().adminImportArchive();
        assertPageIsAvailable();

        kraken.go().adminImportBlackList();
        assertPageIsAvailable();

        kraken.go().adminImportCategory();
        assertPageIsAvailable();

        kraken.go().adminImportFilters();
        assertPageIsAvailable();

        kraken.go().adminImportProducts();
        assertPageIsAvailable();

        kraken.go().adminImportPrice();
        assertPageIsAvailable();

        kraken.go().adminImportImages();
        assertPageIsAvailable();

    }

    private void reports() throws Exception {

        kraken.go().adminReports();
        assertPageIsAvailable();
    }

    private void settings() throws Exception {

        kraken.go().adminSettings();
        assertPageIsAvailable();
    }

    private void marketing() throws Exception {

        kraken.go().adminMarketing();
        assertPageIsAvailable();

        kraken.go().adminMarketingPromoCards();
        assertPageIsAvailable();

        kraken.go().adminMarketingPromoAction();
        assertPageIsAvailable();

        kraken.go().adminMarketingWelcomeBanners();
        assertPageIsAvailable();

        kraken.go().adminMarketingAdvertisement();
        assertPageIsAvailable();

        kraken.go().adminMarketingYandexMarket();
        assertPageIsAvailable();

        kraken.go().adminMarketingCarts();
        assertPageIsAvailable();

        kraken.go().adminMarketingBonusCards();
        assertPageIsAvailable();

        kraken.go().adminMarketingRetailersPrograms();
        assertPageIsAvailable();

        kraken.go().adminMarketingNewCities();
        assertPageIsAvailable();
    }

    private void users() throws Exception {

        kraken.go().adminUsers();
        assertPageIsAvailable();
    }

    private void pages() throws Exception {

        kraken.go().adminPages();
        assertPageIsAvailable();
    }


    @Test(
            description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"regression"},
            priority = 705
    )
    public void successGrantAndRevokeAdminPrivileges() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = Generate.testAdminData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().grantAdminPrivileges();
        kraken.perform().quickLogout();

        kraken.perform().login(testuser);
        kraken.get().page(Pages.Admin.shipments());

        Assert.assertTrue(kraken.detect().isInAdmin(),
                "Пользователю не предоставляются админские права");

        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().revokeAdminPrivileges();
        kraken.perform().quickLogout();

        kraken.perform().login(testuser);
        kraken.get().page(Pages.Admin.shipments());

        Assert.assertFalse(kraken.detect().isInAdmin(),
                "У пользователя не снимаются админские права");

        kraken.perform().quickLogout();

        kraken.cleanup().users(Config.testAdminsList);
    }


    @Test(
            description = "Тест поиска пользователя в админке",
            groups = {"acceptance","regression"},
            priority = 706
    )
    public void successSearchUser() {
        UserData user = Users.getCredentials("user");

        kraken.admin().searchUser(user.getLogin());

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Users.firstUserLogin()), user.getLogin(),
                "Не работает поиск пользователя в админке");
    }


    @Test(
            description = "Тест поиска заказа в админке",
            groups = {"acceptance","regression"},
            priority = 707
    )
    public void successSearchOrder() {
        kraken.get().page("metro");
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        kraken.admin().searchOrder(number);

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                "Не работает поиск заказа в админке");
    }

    @Test(
            description = "Тест смены пароля пользователю",
            groups = {"regression"},
            priority = 708
    )
    public void successChangePassword() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = Generate.testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().changePassword("654321");
        kraken.perform().quickLogout();

        kraken.perform().login(testuser.getLogin(), "654321");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не работает смена пароля пользователю через админку");

        kraken.perform().quickLogout();

        kraken.cleanup().users(Config.testUsersList);
    }

    @Test(
            description = "Тест проставления пользователю флага B2B",
            groups = {"regression"},
            priority = 709
    )
    public void successGrantB2B() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = Generate.testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().grantB2B();

        kraken.admin().searchUser(testuser);

        Assert.assertTrue(kraken.detect().isElementDisplayed(Elements.Admin.Users.firstUserB2BLabel()),
                "Пользователю не проставляется флаг B2B");

        kraken.cleanup().users(Config.testUsersList);
    }

}
