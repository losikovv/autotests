package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;
import ru.instamart.autotests.models.RetailerData;



    // Navigation helper
    // Handles navigation within system under test
    // 'get' methods navigate by getting URLs
    // 'go' methods navigate by making transitions through UI



public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver driver, Environments environment){ super(driver, environment); }



    // ======= SITE =======

    // переход на страницу сайта
    public void getPage(String pageName) {
        getUrl(baseUrl + pageName);
    }

    // переход на витрину ретейлера
    public void getRetailerPage(String retailerName) {
        getUrl(baseUrl + retailerName);
    }

    // переход на витрину ретейлера
    public void getRetailerPage(RetailerData retailerData) {
        getUrl(baseUrl + retailerData.getName());
    }

    // переход на страницу чекаута
    public void getCheckoutPage() {
        getUrl(baseUrl + "checkout/edit?");
        waitForIt(1);
    }

    // переход в профиль
    public void getProfilePage()  {
        getUrl(baseUrl + "user/edit");
    }


    // переходы на лендинги
    public void getLandingPage() {
        getUrl(baseUrl);
    }

    public void getLandingPage(String landingName) {
        getUrl(baseUrl + landingName);
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

    public void goHome() {
        printMessage("Going home ...");
        click(By.className("header-logo"));
    }



    // ======= ADMIN =======

    /**
     * Get page in admin panel
     */
    public void getAdminPage(String pageName) {
        getUrl(baseUrl + "admin/" + pageName);
    }

    /**
     * Get order page in admin panel
     */
    public void getOrderAdminPage(String orderNumber){
        getUrl(baseUrl + "admin/orders/" + orderNumber + "/edit");
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
        click(Elements.HeaderAdmin.profileButton());
    }

    public void goLogout() {
        click(Elements.HeaderAdmin.logoutButton());
    }

    public void goBackToList() {
        click(Elements.HeaderAdmin.backToListButton());
    }

    //========== Меню Админки ==========

    //========== Раздел Заказы и его подразделы ========


    public void goOrders() {
        click(Elements.HeaderAdmin.ordersButton());
    }

    public void goOrdersMulti() {
        goOrders();
        click(Elements.HeaderAdmin.multiOrderButton());
    }

    public void goOrdersExport() {
        goOrders();
        click(Elements.HeaderAdmin.exportButton());
    }

    public void goOrdersVeeroute() {
        goOrders();
        click(Elements.HeaderAdmin.veerouteButton());
    }

    //======== Раздел Магазины и его подразделы =========

    public void goStore() {
        click(Elements.HeaderAdmin.storeButton());
    }


    public void goStoreRetailers() {
        goStore();
        click(Elements.HeaderAdmin.retailersButton());
    }

    public void goStoreZones() {
        goStore();
        click(Elements.HeaderAdmin.zonesButton());
    }


    // =========== Раздел Продукты и его подразделы ==========

    public void goProducts() {
        click(Elements.HeaderAdmin.productsButton());
    }

    public void goSubProducts() {
        goProducts();
        click(Elements.HeaderAdmin.subProductsButton());
    }

    public void goProductsStats() {
        goProducts();
        click(Elements.HeaderAdmin.productsStatsButton());
    }

    public void goProductsOptionTypes() {
        goProducts();
        click(Elements.HeaderAdmin.optionTypesButton());
    }

    public void goProductsProperties() {
        goProducts();
        click(Elements.HeaderAdmin.propertiesButton());
    }

    public void goProductsPrototypes() {
        goProducts();
        click(Elements.HeaderAdmin.prototypesButton());
    }

    public void goProductsBrands() {
        goProducts();
        click(Elements.HeaderAdmin.brandsButtton());
    }

    public void goProductsProducers() {
        goProducts();
        click(Elements.HeaderAdmin.producersButton());
    }

    public void goProductsProducersCountries() {
        goProducts();
        click(Elements.HeaderAdmin.producersCountriesButton());
    }

    //========== Раздел Импорт и его подразделы ========

    public void goImport() {
        click(Elements.HeaderAdmin.importButton());
    }

    public void goImportQueueOfTasks() {
        goImport();
        click(Elements.HeaderAdmin.queueOfTasksButton());
    }

    public void goImportStats() {
        goImport();
        click(Elements.HeaderAdmin.importStatsButton());
    }

    public void goImportArchive() {
        goImport();
        click(Elements.HeaderAdmin.archiveButton());
    }

    public void goImportBlackList() {
        goImport();
        click(Elements.HeaderAdmin.blackListButton());
    }

    public void goImportCategory() {
        goImport();
        click(Elements.HeaderAdmin.categoryButton());
    }

    public void goImportFilters() {
        goImport();
        click(Elements.HeaderAdmin.filtersButton());
    }

    public void goImportProducts() {
        goImport();
        click(Elements.HeaderAdmin.importProductsButton());
    }

    public void goImportPrice() {
        goImport();
        click(Elements.HeaderAdmin.priceButton());
    }

    //========== Раздел отчеты ==============

    public void goReports() {
        click(Elements.HeaderAdmin.reportsButton());
    }

    //========== Раздел настройки ============

    public void goSettings() {
        click(Elements.HeaderAdmin.settingsButton());
    }

    //========= Раздел Маркетинг и его подразделы ===========

    public void goMarketing() {
        click(Elements.HeaderAdmin.marketingButton());
    }

    public void goMarketingPromoCards() {
        goMarketing();
        click(Elements.HeaderAdmin.promoCardsButton());
    }

    public void goMarketingPromoAction() {
        goMarketing();
        click(Elements.HeaderAdmin.promoActionButton());
    }

    public void goMarketingWelcomeBanners() {
        goMarketing();
        click(Elements.HeaderAdmin.welcomeBannersButton());
    }

    public void goMarketingAdvertisement() {
        goMarketing();
        click(Elements.HeaderAdmin.advertisementButton());
    }

    public void goMarketingYandexMarket() {
        goMarketing();
        click(Elements.HeaderAdmin.yandexMarketButton());
    }

    public void goMarketingCarts() {
        goMarketing();
        click(Elements.HeaderAdmin.cartsButton());
    }

    public void goMarketingBonusCards() {
        goMarketing();
        click(Elements.HeaderAdmin.bonusCardsButton());
    }

    public void goMarketingRetailersPrograms() {
        goMarketing();
        click(Elements.HeaderAdmin.retailersProgramsButton());
    }

    public void goMarketingNewCities() {
        goMarketing();
        click(Elements.HeaderAdmin.newCitiesButton());
    }

    //========== Раздел пользователи ===========

    public void goUsers() {
        goMarketing();
        click(Elements.HeaderAdmin.usersButton());
    }

    //========== Раздел Страницы ===========

    public void goPages() {
        goMarketing();
        click(Elements.HeaderAdmin.pagesButton());
    }



}
