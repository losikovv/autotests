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
import static ru.instamart.autotests.application.Config.testOrder;
import static ru.instamart.autotests.application.Config.testShipment;
import static ru.instamart.autotests.appmanager.ApplicationManager.session;


// Тесты админки


public class Administration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() throws Exception {
        kraken.reach().admin();
    }


    @Test(  enabled = enableAdministrationTests,
            description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 1301
    )
    public void noAccessAdministrationWithoutAdminPrivileges() throws Exception {
        kraken.perform().loginAs(session.user);

        assertPageIsUnavailable(Pages.Admin.shipments());

        kraken.perform().quickLogout();
    }


    @Test(  enabled = enableAdministrationTests,
            description = "Тест возобновления и отмены заказа через админку",
            groups = {"acceptance","regression"},
            priority = 1302
    )
    public void successResumeAndCancelOrder() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        kraken.get().ordersPage();
        if(kraken.detect().isElementPresent(Elements.Site.UserProfile.OrdersPage.placeholder())) {
            kraken.perform().printMessage("Нет заказов, делаем новый\n");
            kraken.get().page("metro");
            kraken.perform().order();
        } else {
            if(kraken.detect().isLastOrderActive()) {
                kraken.perform().click(Elements.Site.UserProfile.OrdersPage.lastOrderActionButton(1));
            }
            kraken.perform().click(Elements.Site.UserProfile.OrdersPage.lastOrderDetailsButton());
        }
        kraken.get().adminOrderDetailsPage(kraken.grab().currentOrderNumber());

        softAssert.assertTrue(kraken.detect().isOrderCanceled(),
                "\nНе выполнились предусловия - заказ уже активен");

        kraken.admin().resumeOrder();

        softAssert.assertFalse(kraken.detect().isOrderCanceled(),
                "\nНе возобновляется заказ через админку");

        kraken.admin().cancelOrder();

        softAssert.assertTrue(kraken.detect().isOrderCanceled(),
                "\nНе отменяется заказ через админку");

        softAssert.assertAll();
    }


    @Test(  enabled = enableAdministrationTests,
            description = "Тест шапки админки",
            groups = {"regression"},
            priority = 1303
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

    private void imports() {

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


    @Test(  enabled = enableAdministrationTests,
            description = "Тест предоставления и отзыва админских прав пользователю",
            groups = {"regression"},
            priority = 1304
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
            description = "Тест поиска пользователя в админке",
            groups = {"acceptance","regression"},
            priority = 1305
    )
    public void successSearchUser() throws Exception {

        kraken.admin().searchUser(Users.superuser());

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Users.firstUserLogin()), Users.superuser().getEmail(),
                "Не работает поиск пользователя в админке");
    }


    @Test(  enabled = enableAdministrationTests,
            description = "Тест поиска заказа по номеру заказа в админке",
            groups = {"acceptance","regression"},
            priority = 1306
    )
    public void successSearchOrderByOrderNumber() throws Exception {

        kraken.admin().searchOrder(testOrder);

        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder()),
                "Не работает поиск заказа в админке, пустой результат поиска\n");

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), testOrder,
                "Не работает поиск заказа в админке, найден не тот шипмент\n");
    }


    @Test(  enabled = enableAdministrationTests,
            description = "Тест поиска заказа по номеру шипмента в админке",
            groups = {"acceptance","regression"},
            priority = 1307
    )
    public void successSearchOrderByShipmentNumber() throws Exception {

        kraken.admin().searchOrder(testShipment);

        Assert.assertFalse(kraken.detect().isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder()),
                "Не работает поиск шипмента в админке, пустой результат поиска\n");

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstShipmentNumberInTable()), testShipment,
                "Не работает поиск шипмента в админке, найден не тот шипмент\n");
    }


    @Test(  enabled = enableAdministrationTests,
            description = "Тест смены пароля пользователю",
            groups = {"regression"},
            priority = 1308,
            dataProvider = "generateUserData"
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
            priority = 1309
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
            priority = 1310
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
            description = "Тест поиска B2B заказа в админке",
            groups = {"regression"},
            priority = 1311
    )
    public void successSearchB2BOrder() throws Exception {
        kraken.perform().quickLogout();
        UserData testuser = generate.testCredentials("user");
        kraken.perform().registration(testuser);

        kraken.admin().editUser(testuser);
        kraken.admin().grantB2B();
        kraken.perform().quickLogout();

        kraken.perform().authorisation(testuser);
        kraken.perform().order();
        String number = kraken.grab().currentOrderNumber();

        kraken.admin().searchOrder(number,true);

        Assert.assertEquals(kraken.grab().text(Elements.Admin.Shipments.firstOrderNumberInTable()), number,
                "Не работает поиск B2B заказа в админке");

        kraken.cleanup().orders();
    }


    @Test(  enabled = enableAdministrationTests,
            description = "Тест снятия B2B флага у пользователя",
            groups = {"regression"},
            priority = 1312
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

        kraken.cleanup().orders();
        softAssert.assertAll();
    }
}
