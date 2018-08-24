package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


// Тесты админки



public class Administration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel(){
        app.getSessionHelper().getUrlAsAdmin("https://instamart.ru/admin/shipments"); // TODO параметризовать окружение
    }


    @Test(
            description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 700
    )
    public void adminPanelUnreacheableWithoutPrivileges() throws Exception {
        app.getSessionHelper().doLogout();
        app.getSessionHelper().doLoginAs("user");

        assertPageIsUnreachable("https://instamart.ru/admin/shipments"); // TODO параметризовать окружение

        app.getSessionHelper().doLogout();
    }


    @Test(
            description = "Тест возобновления заказа через админку",
            groups = {"acceptance","regression"},
            priority = 701
    )
    public void resumeOrder() throws Exception {

        String orderNumber = "R124857258"; //TODO убрать хардкод номера заказа, делать новый тестовый заказ перед тестами
        app.getNavigationHelper().getOrderAdminPage(orderNumber);

        // Assert order is canceled
        Assert.assertTrue(app.getAdministrationHelper().isOrderCanceled(),
                "The order is already active\n");

        app.getAdministrationHelper().resumeOrder();

        // Assert order isn't canceled
        Assert.assertFalse(app.getAdministrationHelper().isOrderCanceled(),
                "Can't approve the order was resumed, check manually\n");
    }


    @Test(
            description = "Тест отмены заказа через админку",
            groups = {"acceptance","regression"},
            priority = 702
    )
    public void cancelOrder() throws Exception {

        String orderNumber = "R124857258"; // TODO заменить на номер заказа тестового пользователя
        //TODO убрать хардкод номера заказа, делать новый тестовый заказ перед тестами

        app.getNavigationHelper().getOrderAdminPage(orderNumber);

        Assert.assertFalse(app.getAdministrationHelper().isOrderCanceled(),
                "The order is already canceled\n");

        app.getSessionHelper().cancelOrder(); // TODO перенести метод в AdministrationHelper

        Assert.assertTrue(app.getAdministrationHelper().isOrderCanceled(),
                "Can't approve the order was canceled, check manually\n");
    }


    @Test(priority = 703)
    public void cancelTestOrders() throws Exception {
        app.getSessionHelper().cancelAllTestOrders(); // TODO перенести метод в AdministrationHelper
    }



    @Test(
            description = "Тест шапки админки",
            groups = {"regression"},
            priority = 704

    )

    //Спрятать все лишнее

    public void checkHeader() throws Exception {

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
        app.getNavigationHelper().goOrders();
        assertPageIsAvailable();

        app.getNavigationHelper().goOrdersMulti();
        assertPageIsAvailable();

        app.getNavigationHelper().goOrdersExport();
        assertPageIsAvailable();

        app.getNavigationHelper().goOrdersVeeroute();
        assertPageIsAvailable();
    }

    private void stores() throws Exception {
        app.getNavigationHelper().goStore();
        assertPageIsAvailable();

        app.getNavigationHelper().goStoreRetailers();
        assertPageIsAvailable();

        app.getNavigationHelper().goStoreZones();
        assertPageIsAvailable();
    }

    private void products() throws Exception {
        app.getNavigationHelper().goProducts();
        assertPageIsAvailable();

        app.getNavigationHelper().goSubProducts();
        assertPageIsAvailable();

        app.getNavigationHelper().goProductsStats();
        assertPageIsAvailable();

        app.getNavigationHelper().goProductsOptionTypes();
        assertPageIsAvailable();

        app.getNavigationHelper().goProductsProperties();
        assertPageIsAvailable();

        app.getNavigationHelper().goProductsPrototypes();
        assertPageIsAvailable();

        app.getNavigationHelper().goProductsBrands();
        assertPageIsAvailable();

        app.getNavigationHelper().goProductsProducers();
        assertPageIsAvailable();

        app.getNavigationHelper().goProductsProducersCountries();
        assertPageIsAvailable();
    }

    private void imports() throws Exception {
        app.getNavigationHelper().goImport();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportQueueOfTasks();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportStats();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportArchive();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportBlackList();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportCategory();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportFilters();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportProducts();
        assertPageIsAvailable();

        app.getNavigationHelper().goImportPrice();
        assertPageIsAvailable();

    }

    private void reports() throws Exception {
        app.getNavigationHelper().goReports();
        assertPageIsAvailable();
    }

    private void settings() throws Exception {
        app.getNavigationHelper().goSettings();
        assertPageIsAvailable();
    }

    private void marketing() throws Exception {
        app.getNavigationHelper().goMarketing();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingPromoCards();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingPromoAction();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingWelcomeBanners();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingAdvertisement();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingYandexMarket();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingCarts();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingBonusCards();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingRetailersPrograms();
        assertPageIsAvailable();

        app.getNavigationHelper().goMarketingNewCities();
        assertPageIsAvailable();
    }

    private void users() throws Exception {
        app.getNavigationHelper().goUsers();
        assertPageIsAvailable();
    }

    private void pages() throws Exception {
        app.getNavigationHelper().goPages();
        assertPageIsAvailable();
    }

}
