package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.UserData;


// Тесты админки


public class Administration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() throws Exception {
        kraken.perform().reachAdmin();
    }


    @Test(
            description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 1201
    )
    public void noAccessAdministrationWithoutAdminPrivileges() throws Exception {
        kraken.perform().loginAs("user");

        assertPageIsUnavailable(Pages.Admin.shipments());

        kraken.perform().quickLogout();
    }


    @Test(
            description = "Тест возобновления заказа через админку",
            groups = {"acceptance","regression"},
            priority = 1202
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
            priority = 1203
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
            priority = 1204
    )
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
            priority = 1205
    )
    public void successGrantAndRevokeAdminPrivileges() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = kraken.generate().testAdminData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().grantAdminPrivileges();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.get().page(Pages.Admin.shipments());

        Assert.assertTrue(kraken.detect().isInAdmin(),
                "Пользователю не предоставляются админские права");

        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().revokeAdminPrivileges();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.get().page(Pages.Admin.shipments());

        Assert.assertFalse(kraken.detect().isInAdmin(),
                "У пользователя не снимаются админские права");

        kraken.perform().quickLogout();

        kraken.cleanup().users();
    }


    @Test(
            description = "Тест поиска пользователя в админке",
            groups = {"acceptance","regression"},
            priority = 1206
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
            priority = 1207
    )
    public void successSearchOrder() {
        kraken.get().page("metro");
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        kraken.admin().searchOrder(number);

        // TODO добавить проверку на наличие заказов в результатах поиска
        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                "Не работает поиск заказа в админке");
    }


    @Test(
            description = "Тест смены пароля пользователю",
            groups = {"regression"},
            priority = 1208
    )
    public void successChangePassword() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = kraken.generate().testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().changePassword("654321");
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser.getLogin(), "654321");

        Assert.assertTrue(kraken.detect().isUserAuthorised(),
                "Не удалось авторизоваться пользователем после смены пароля через админку");

        kraken.perform().quickLogout();

        kraken.cleanup().users();
    }


    @Test(
            description = "Тест проставления пользователю флага B2B",
            groups = {"regression"},
            priority = 1209
    )
    public void successGrantB2BStatus() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = kraken.generate().testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().grantB2B();
        kraken.perform().refresh();

        Assert.assertTrue(kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.b2bCheckbox()),
                "Пользователю не проставляется флаг B2B");

        kraken.cleanup().users();
    }


    @Test(
            description = "Тест поиска B2B пользователя в админке",
            groups = {"regression"},
            priority = 1210
    )
    public void successSearchB2BUser() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = kraken.generate().testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().grantB2B();

        kraken.admin().searchB2BUser(testuser);

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Users.firstUserLogin()), testuser.getLogin(),
                "Не работает поиск B2B пользователя в админке");

        Assert.assertTrue(kraken.detect().isElementDisplayed(Elements.Admin.Users.firstUserB2BLabel()),
                "У пользователя не отображается B2B метка");

        kraken.cleanup().users();
    }


    @Test(
            description = "Тест поиска B2B заказа в админке",
            groups = {"regression"},
            priority = 1211
    )
    public void successSearchB2BOrder() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = kraken.generate().testUserData();
        kraken.perform().registration(testuser);
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchUser(testuser);
        kraken.admin().editFirstUserInList();
        kraken.admin().grantB2B();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();
        kraken.perform().quickLogout();

        kraken.perform().loginAs("admin");
        kraken.admin().searchB2BOrder(number);

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                "Не работает поиск B2B заказа в админке");

        kraken.cleanup().all();
    }

    // TODO добавить тест на снятие B2B-галки и проверку что пользователь перестает искаться по признаку b2b, но его заказы остаются с флогом и ищутся по признаку b2b

}
