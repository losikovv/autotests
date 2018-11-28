package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class NavigationHelper extends HelperBase {

    private ApplicationManager kraken;

    NavigationHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Навигация переходами по элементам на страницах */
    public void to(Elements element){
        kraken.perform().click(Elements.locator());
    }

    // TODO сделать метод go принимающий массив элементов и кликающий их по очереди
    // TODO public void go(Elements[] elements){ }


    //========== Раздел Заказы и его подразделы ========

    public void adminOrders() {
        Elements.Admin.Header.Menu.ordersButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminOrdersMulti() {
        adminOrders();
        Elements.Admin.Header.SubmenuOrders.multiOrderButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminOrdersExport() {
        adminOrders();
        Elements.Admin.Header.SubmenuOrders.exportButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminOrdersVeeroute() {
        adminOrders();
        Elements.Admin.Header.SubmenuOrders.veerouteButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }


    //======== Раздел Магазины и его подразделы =========

    public void adminStores() {
        Elements.Admin.Header.Menu.storesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }


    public void adminStoresRetailers() {
        adminStores();
        Elements.Admin.Header.SubmenuStores.retailersButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminStoresZones() {
        adminStores();
        Elements.Admin.Header.SubmenuStores.zonesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }


    // =========== Раздел Продукты и его подразделы ==========

    public void adminProducts() {
        Elements.Admin.Header.Menu.productsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminSubProducts() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.subProductsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsStats() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.productsStatsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsOptionTypes() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.optionTypesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsProperties() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.propertiesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsPrototypes() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.prototypesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsBrands() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.brandsButtton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsProducers() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.producersButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsProducersCountries() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.producersCountriesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел Импорт и его подразделы ========

    public void adminImport() {
        Elements.Admin.Header.Menu.importButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportQueueOfTasks() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.queueOfTasksButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportStats() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.importStatsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportArchive() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.archiveButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportBlackList() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.blackListButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportCategory() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.categoryButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportFilters() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.filtersButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportProducts() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.importProductsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportPrice() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.priceButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportImages() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.imagesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел отчеты ==============

    public void adminReports() {
        Elements.Admin.Header.Menu.reportsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел настройки ============

    public void adminSettings() {
        Elements.Admin.Header.Menu.settingsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========= Раздел Маркетинг и его подразделы ===========

    public void adminMarketing() {
        Elements.Admin.Header.Menu.marketingButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingPromoCards() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.promoCardsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingPromoAction() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.promoActionButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingWelcomeBanners() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.welcomeBannersButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingAdvertisement() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.advertisementButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingYandexMarket() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.yandexMarketButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingCarts() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.cartsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingBonusCards() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.bonusCardsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingRetailersPrograms() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.retailersProgramsButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingNewCities() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.newCitiesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел пользователи ===========

    public void adminUsers() {
        Elements.Admin.Header.Menu.usersButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел Страницы ===========

    public void adminPages() {
        Elements.Admin.Header.Menu.pagesButton();
        printMessage("Going to " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }
}
