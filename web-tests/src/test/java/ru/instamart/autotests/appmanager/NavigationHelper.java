package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;
import ru.instamart.autotests.configuration.Pages;
import ru.instamart.autotests.models.RetailerData;



    // Navigation helper
    // Handles navigation within system under test
    // 'get' methods navigate by getting URLs
    // 'go' methods navigate by making transitions through UI



public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver, Environments environment){ super(driver, environment); }


    /** Перейти на базовый URL */

    public void baseUrl() {
        url(fullBaseUrl);
    }


    /** Перейти на указанный URL */

    public void url(String url) {
        if (url.equals(fullBaseUrl)) printMessage("Getting baseURL " + url + "\n");
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            printMessage("Can't get " + url + " by timeout");
        }
    }



    // ======= SITE =======


    /** Навигация переходами по ссылкам */

    public void get(String page) {
        url(fullBaseUrl + page);
    }

    public void get(Pages page) {
        String path = Pages.getPagePath();
        url(fullBaseUrl + path);
    }



    /** Навигация переходами по элементам на страницах */

    public void go(Elements element){
        click(Elements.getLocator());
    }

    // TODO сделать метод go принимающий массив элементов и кликающий их по очереди
    // TODO public void go(Elements[] elements){ }

    // переход на витрину ретейлера
    public void retailerPage(String retailerName) {
        url(baseUrl + retailerName);
    }

    // переход на витрину ретейлера
    public void retailerPage(RetailerData retailerData) {
        url(baseUrl + retailerData.getName());
    }

    // переход на страницу чекаута
    public void checkoutPage() {
        url(baseUrl + "checkout/edit?");
        waitFor(1);
    }

    // переход в профиль
    public void getProfilePage()  {
        url(baseUrl + "user/edit");
    }


    // переходы на лендинги

    public void getLandingPage(String landingName) {
        url(baseUrl + landingName);
    }


    public void goToProfile() {
        // только для авторизованного
        // клик по кнопке Профиль
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        // клик по кнопке Профиль
        click(By.linkText("Профиль"));
    }

    public void goToHomepage() {
        // клик по кнопке Главная
        click(By.linkText("Главная"));
    }







    // ======= ADMIN =======

    /**
     * Get page in admin panel
     */
    public void getAdminPage(String pageName) {
        url(baseUrl + "admin/" + pageName);
    }

    /**
     * Get order page in admin panel
     */
    public void getOrderAdminPage(String orderNumber){
        url(baseUrl + "admin/orders/" + orderNumber + "/edit");
    }

    /**
     * Get page with the list of test users in admin panel
     */
    public void getTestUsersAdminPage(){
        getAdminPage("users?q%5Bemail_cont%5D=testuser%40example.com");
    }

    /**
     * Get page with the list of test orders in admin panel
     */
    public void getTestOrdersAdminPage(){
        getAdminPage("shipments?search%5Bemail%5D=autotestuser%40instamart.ru&search%5Bonly_completed%5D=1&search%5Bstate%5D%5B%5D=ready");
    }

    //======== Go Методы для админки =========

    //======== Шапка Админки ==========

    public void goProfile() {
        click(Elements.Admin.Header.profileButton());
    }

    public void goLogout() {
        click(Elements.Admin.Header.logoutButton());
    }

    public void goBackToList() {
        click(Elements.Admin.Header.backToListButton());
    }

    //========== Меню Админки ==========

    //========== Раздел Заказы и его подразделы ========


    public void goOrders() {
        Elements.Admin.Header.Menu.ordersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goOrdersMulti() {
        goOrders();
        Elements.Admin.Header.SubmenuOrders.multiOrderButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goOrdersExport() {
        goOrders();
        Elements.Admin.Header.SubmenuOrders.exportButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goOrdersVeeroute() {
        goOrders();
        Elements.Admin.Header.SubmenuOrders.veerouteButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //======== Раздел Магазины и его подразделы =========

    public void goStore() {
        Elements.Admin.Header.Menu.storeButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }


    public void goStoreRetailers() {
        goStore();
        Elements.Admin.Header.SubmenuStores.retailersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goStoreZones() {
        goStore();
        Elements.Admin.Header.SubmenuStores.zonesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }


    // =========== Раздел Продукты и его подразделы ==========

    public void goProducts() {
        Elements.Admin.Header.Menu.productsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goSubProducts() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.subProductsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsStats() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.productsStatsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsOptionTypes() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.optionTypesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsProperties() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.propertiesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsPrototypes() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.prototypesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsBrands() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.brandsButtton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsProducers() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.producersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsProducersCountries() {
        goProducts();
        Elements.Admin.Header.SubmenuProducts.producersCountriesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел Импорт и его подразделы ========

    public void goImport() {
        Elements.Admin.Header.Menu.importButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportQueueOfTasks() {
        goImport();
        Elements.Admin.Header.SubmenuImport.queueOfTasksButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportStats() {
        goImport();
        Elements.Admin.Header.SubmenuImport.importStatsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportArchive() {
        goImport();
        Elements.Admin.Header.SubmenuImport.archiveButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportBlackList() {
        goImport();
        Elements.Admin.Header.SubmenuImport.blackListButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportCategory() {
        goImport();
        Elements.Admin.Header.SubmenuImport.categoryButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportFilters() {
        goImport();
        Elements.Admin.Header.SubmenuImport.filtersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportProducts() {
        goImport();
        Elements.Admin.Header.SubmenuImport.importProductsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportPrice() {
        goImport();
        Elements.Admin.Header.SubmenuImport.priceButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportImages() {
        goImport();
        Elements.Admin.Header.SubmenuImport.imagesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел отчеты ==============

    public void goReports() {
        Elements.Admin.Header.Menu.reportsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел настройки ============

    public void goSettings() {
        Elements.Admin.Header.Menu.settingsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========= Раздел Маркетинг и его подразделы ===========

    public void goMarketing() {
        Elements.Admin.Header.Menu.marketingButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingPromoCards() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.promoCardsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingPromoAction() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.promoActionButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingWelcomeBanners() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.welcomeBannersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingAdvertisement() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.advertisementButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingYandexMarket() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.yandexMarketButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingCarts() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.cartsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingBonusCards() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.bonusCardsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingRetailersPrograms() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.retailersProgramsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingNewCities() {
        goMarketing();
        Elements.Admin.Header.SubmenuMarketing.newCitiesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел пользователи ===========

    public void goUsers() {
        Elements.Admin.Header.Menu.usersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел Страницы ===========

    public void goPages() {
        Elements.Admin.Header.Menu.pagesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }



}
