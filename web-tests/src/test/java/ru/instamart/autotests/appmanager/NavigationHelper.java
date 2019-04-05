package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;

public class NavigationHelper extends HelperBase {

    NavigationHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
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
        ElementData button = Elements.Admin.Header.Menu.ordersButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminOrdersMulti() {
        adminOrders();
        ElementData button = Elements.Admin.Header.SubmenuOrders.multiOrderButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminOrdersExport() {
        adminOrders();
        ElementData button = Elements.Admin.Header.SubmenuOrders.exportButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminOrdersVeeroute() {
        adminOrders();
        ElementData button = Elements.Admin.Header.SubmenuOrders.veerouteButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }


    //======== Раздел Магазины и его подразделы =========

    public void adminStores() {
        ElementData button = Elements.Admin.Header.Menu.storesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }


    public void adminStoresRetailers() {
        adminStores();
        ElementData button = Elements.Admin.Header.SubmenuStores.retailersButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminStoresZones() {
        adminStores();
        ElementData button = Elements.Admin.Header.SubmenuStores.zonesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }


    // =========== Раздел Продукты и его подразделы ==========

    public void adminProducts() {
        ElementData button = Elements.Admin.Header.Menu.productsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminSubProducts() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.subProductsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminProductsStats() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.productsStatsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminProductsOptionTypes() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.optionTypesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminProductsProperties() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.propertiesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminProductsPrototypes() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.prototypesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminProductsBrands() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.brandsButtton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminProductsProducers() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.producersButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminProductsProducersCountries() {
        adminProducts();
        ElementData button = Elements.Admin.Header.SubmenuProducts.producersCountriesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    //========== Раздел Импорт и его подразделы ========

    public void adminImport() {
        ElementData button = Elements.Admin.Header.Menu.importButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportQueueOfTasks() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.queueOfTasksButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportStats() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.importStatsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportArchive() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.archiveButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportBlackList() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.blackListButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportCategory() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.categoryButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportFilters() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.filtersButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportProducts() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.importProductsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportPrice() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.priceButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminImportImages() {
        adminImport();
        ElementData button = Elements.Admin.Header.SubmenuImport.imagesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    //========== Раздел отчеты ==============

    public void adminReports() {
        ElementData button = Elements.Admin.Header.Menu.reportsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    //========== Раздел настройки ============

    public void adminSettings() {
        ElementData button = Elements.Admin.Header.Menu.settingsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    //========= Раздел Маркетинг и его подразделы ===========

    public void adminMarketing() {
        ElementData button = Elements.Admin.Header.Menu.marketingButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingPromoCards() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.promoCardsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingPromoAction() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.promoActionButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingWelcomeBanners() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.welcomeBannersButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingAdvertisement() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.advertisementButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingYandexMarket() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.yandexMarketButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingCarts() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.cartsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingBonusCards() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.bonusCardsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingRetailersPrograms() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.retailersProgramsButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    public void adminMarketingNewCities() {
        adminMarketing();
        ElementData button = Elements.Admin.Header.SubmenuMarketing.newCitiesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    //========== Раздел пользователи ===========

    public void adminUsers() {
        ElementData button = Elements.Admin.Header.Menu.usersButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }

    //========== Раздел Страницы ===========

    public void adminPages() {
        ElementData button = Elements.Admin.Header.Menu.pagesButton();
        printMessage("Переходим в " + button.getText() + "...");
        kraken.perform().click(button.getLocator());
    }
}
