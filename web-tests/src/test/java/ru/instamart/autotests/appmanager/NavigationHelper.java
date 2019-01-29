package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;

public class NavigationHelper extends HelperBase {

    private ApplicationManager kraken;

    NavigationHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    // TODO сделать метод to принимающий массив элементов и кликающий их по очереди
    // TODO public void to(Elements[] elements){ }

    // TODO доделать
    public abstract class to {

        public void lastOrderPage() {
            kraken.get().url(baseUrl + "user/orders");
            kraken.perform().click(By.xpath("//*[@id='wrap']/div/div/div/div[2]/div[1]/div/div/div[1]/div/div/div[1]/div[2]/a"));
        }
    }

    //========== Раздел Заказы и его подразделы ========

    public void adminOrders() {
        Elements.Admin.Header.Menu.ordersButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminOrdersMulti() {
        adminOrders();
        Elements.Admin.Header.SubmenuOrders.multiOrderButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminOrdersExport() {
        adminOrders();
        Elements.Admin.Header.SubmenuOrders.exportButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminOrdersVeeroute() {
        adminOrders();
        Elements.Admin.Header.SubmenuOrders.veerouteButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }


    //======== Раздел Магазины и его подразделы =========

    public void adminStores() {
        Elements.Admin.Header.Menu.storesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }


    public void adminStoresRetailers() {
        adminStores();
        Elements.Admin.Header.SubmenuStores.retailersButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminStoresZones() {
        adminStores();
        Elements.Admin.Header.SubmenuStores.zonesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }


    // =========== Раздел Продукты и его подразделы ==========

    public void adminProducts() {
        Elements.Admin.Header.Menu.productsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminSubProducts() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.subProductsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsStats() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.productsStatsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsOptionTypes() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.optionTypesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsProperties() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.propertiesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsPrototypes() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.prototypesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsBrands() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.brandsButtton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsProducers() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.producersButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminProductsProducersCountries() {
        adminProducts();
        Elements.Admin.Header.SubmenuProducts.producersCountriesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел Импорт и его подразделы ========

    public void adminImport() {
        Elements.Admin.Header.Menu.importButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportQueueOfTasks() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.queueOfTasksButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportStats() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.importStatsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportArchive() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.archiveButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportBlackList() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.blackListButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportCategory() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.categoryButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportFilters() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.filtersButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportProducts() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.importProductsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportPrice() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.priceButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminImportImages() {
        adminImport();
        Elements.Admin.Header.SubmenuImport.imagesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел отчеты ==============

    public void adminReports() {
        Elements.Admin.Header.Menu.reportsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел настройки ============

    public void adminSettings() {
        Elements.Admin.Header.Menu.settingsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========= Раздел Маркетинг и его подразделы ===========

    public void adminMarketing() {
        Elements.Admin.Header.Menu.marketingButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingPromoCards() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.promoCardsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingPromoAction() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.promoActionButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingWelcomeBanners() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.welcomeBannersButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingAdvertisement() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.advertisementButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingYandexMarket() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.yandexMarketButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingCarts() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.cartsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingBonusCards() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.bonusCardsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingRetailersPrograms() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.retailersProgramsButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    public void adminMarketingNewCities() {
        adminMarketing();
        Elements.Admin.Header.SubmenuMarketing.newCitiesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел пользователи ===========

    public void adminUsers() {
        Elements.Admin.Header.Menu.usersButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }

    //========== Раздел Страницы ===========

    public void adminPages() {
        Elements.Admin.Header.Menu.pagesButton();
        printMessage("Переходим в " + Elements.text() + "...");
        kraken.perform().click(Elements.locator());
    }
}
