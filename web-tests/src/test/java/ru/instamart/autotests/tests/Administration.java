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
        kraken.get().getOrderAdminPage(orderNumber);

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

        kraken.get().getOrderAdminPage(orderNumber);

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
        kraken.get().goOrders();
        assertPageIsAvailable();

        kraken.get().goOrdersMulti();
        assertPageIsAvailable();

        kraken.get().goOrdersVeeroute();
        assertPageIsAvailable();
    }

    private void stores() throws Exception {
        kraken.get().goStore();
        assertPageIsAvailable();

        kraken.get().goStoreRetailers();
        assertPageIsAvailable();

        kraken.get().goStoreZones();
        assertPageIsAvailable();
    }

    private void products() throws Exception {
        kraken.get().goProducts();
        assertPageIsAvailable();

        kraken.get().goSubProducts();
        assertPageIsAvailable();

        kraken.get().goProductsStats();
        assertPageIsAvailable();

        kraken.get().goProductsOptionTypes();
        assertPageIsAvailable();

        kraken.get().goProductsProperties();
        assertPageIsAvailable();

        kraken.get().goProductsPrototypes();
        assertPageIsAvailable();

        kraken.get().goProductsBrands();
        assertPageIsAvailable();

        kraken.get().goProductsProducers();
        assertPageIsAvailable();

        kraken.get().goProductsProducersCountries();
        assertPageIsAvailable();
    }

    private void imports() throws Exception {
        kraken.get().goImport();
        assertPageIsAvailable();

        kraken.get().goImportQueueOfTasks();
        assertPageIsAvailable();

        kraken.get().goImportStats();
        assertPageIsAvailable();

        kraken.get().goImportArchive();
        assertPageIsAvailable();

        kraken.get().goImportBlackList();
        assertPageIsAvailable();

        kraken.get().goImportCategory();
        assertPageIsAvailable();

        kraken.get().goImportFilters();
        assertPageIsAvailable();

        kraken.get().goImportProducts();
        assertPageIsAvailable();

        kraken.get().goImportPrice();
        assertPageIsAvailable();

        kraken.get().goImportImages();
        assertPageIsAvailable();

    }

    private void reports() throws Exception {
        kraken.get().goReports();
        assertPageIsAvailable();
    }

    private void settings() throws Exception {
        kraken.get().goSettings();
        assertPageIsAvailable();
    }

    private void marketing() throws Exception {
        kraken.get().goMarketing();
        assertPageIsAvailable();

        kraken.get().goMarketingPromoCards();
        assertPageIsAvailable();

        kraken.get().goMarketingPromoAction();
        assertPageIsAvailable();

        kraken.get().goMarketingWelcomeBanners();
        assertPageIsAvailable();

        kraken.get().goMarketingAdvertisement();
        assertPageIsAvailable();

        kraken.get().goMarketingYandexMarket();
        assertPageIsAvailable();

        kraken.get().goMarketingCarts();
        assertPageIsAvailable();

        kraken.get().goMarketingBonusCards();
        assertPageIsAvailable();

        kraken.get().goMarketingRetailersPrograms();
        assertPageIsAvailable();

        kraken.get().goMarketingNewCities();
        assertPageIsAvailable();
    }

    private void users() throws Exception {
        kraken.get().goUsers();
        assertPageIsAvailable();
    }

    private void pages() throws Exception {
        kraken.get().goPages();
        assertPageIsAvailable();
    }

}
