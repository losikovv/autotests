package ru.instamart.autotests.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.autotests.configuration.Pages;


// Тесты админки


public class Administration extends TestBase {


    @BeforeMethod(alwaysRun = true)
    public void reachAdministrationPanel() throws Exception {
        kraken.getSessionHelper().reachAdmin(Pages.Admin.shipments());
    }


    @Test(
            description = "Тест недоступности админки пользователю без админ. прав",
            groups = {"acceptance","regression"},
            priority = 700
    )
    public void adminPanelUnreacheableWithoutPrivileges() throws Exception {
        kraken.getSessionHelper().doLogout();
        kraken.getSessionHelper().doLoginAs("user");

        assertPageIsUnreachable(Pages.Admin.shipments());

        kraken.getSessionHelper().doLogout();
    }


    @Test(
            description = "Тест возобновления заказа через админку",
            groups = {"acceptance","regression"},
            priority = 701
    )
    public void resumeOrder() throws Exception {

        String orderNumber = "R124857258"; //TODO убрать хардкод номера заказа, делать новый тестовый заказ перед тестами
        kraken.getNavigationHelper().getOrderAdminPage(orderNumber);

        // Assert order is canceled
        Assert.assertTrue(kraken.admin().isOrderCanceled(),
                "The order is already active\n");

        kraken.admin().resumeOrder();

        // Assert order isn't canceled
        Assert.assertFalse(kraken.admin().isOrderCanceled(),
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

        kraken.getNavigationHelper().getOrderAdminPage(orderNumber);

        Assert.assertFalse(kraken.admin().isOrderCanceled(),
                "The order is already canceled\n");

        kraken.getSessionHelper().cancelOrder();

        Assert.assertTrue(kraken.admin().isOrderCanceled(),
                "Can't approve the order was canceled, check manually\n");
    }


    @Test(priority = 703)
    public void cancelTestOrders() throws Exception {
        kraken.getSessionHelper().cancelOrders(Pages.Admin.Shipments.testOrdersList());
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
        kraken.getNavigationHelper().goOrders();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goOrdersMulti();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goOrdersVeeroute();
        assertPageIsAvailable();
    }

    private void stores() throws Exception {
        kraken.getNavigationHelper().goStore();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goStoreRetailers();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goStoreZones();
        assertPageIsAvailable();
    }

    private void products() throws Exception {
        kraken.getNavigationHelper().goProducts();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goSubProducts();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goProductsStats();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goProductsOptionTypes();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goProductsProperties();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goProductsPrototypes();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goProductsBrands();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goProductsProducers();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goProductsProducersCountries();
        assertPageIsAvailable();
    }

    private void imports() throws Exception {
        kraken.getNavigationHelper().goImport();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportQueueOfTasks();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportStats();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportArchive();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportBlackList();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportCategory();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportFilters();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportProducts();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportPrice();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goImportImages();
        assertPageIsAvailable();

    }

    private void reports() throws Exception {
        kraken.getNavigationHelper().goReports();
        assertPageIsAvailable();
    }

    private void settings() throws Exception {
        kraken.getNavigationHelper().goSettings();
        assertPageIsAvailable();
    }

    private void marketing() throws Exception {
        kraken.getNavigationHelper().goMarketing();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingPromoCards();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingPromoAction();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingWelcomeBanners();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingAdvertisement();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingYandexMarket();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingCarts();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingBonusCards();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingRetailersPrograms();
        assertPageIsAvailable();

        kraken.getNavigationHelper().goMarketingNewCities();
        assertPageIsAvailable();
    }

    private void users() throws Exception {
        kraken.getNavigationHelper().goUsers();
        assertPageIsAvailable();
    }

    private void pages() throws Exception {
        kraken.getNavigationHelper().goPages();
        assertPageIsAvailable();
    }

}
