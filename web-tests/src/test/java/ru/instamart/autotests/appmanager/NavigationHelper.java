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

    // ========= Подвал сайта =========

    public void goFooterAboutCompany () {
        Elements.Footer.aboutCompanyButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());

    }

    public void



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
        Elements.HeaderAdmin.ordersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goOrdersMulti() {
        goOrders();
        Elements.HeaderAdmin.multiOrderButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goOrdersExport() {
        goOrders();
        Elements.HeaderAdmin.exportButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goOrdersVeeroute() {
        goOrders();
        Elements.HeaderAdmin.veerouteButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //======== Раздел Магазины и его подразделы =========

    public void goStore() {
        Elements.HeaderAdmin.storeButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }


    public void goStoreRetailers() {
        goStore();
        Elements.HeaderAdmin.retailersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goStoreZones() {
        goStore();
        Elements.HeaderAdmin.zonesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }


    // =========== Раздел Продукты и его подразделы ==========

    public void goProducts() {
        Elements.HeaderAdmin.productsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goSubProducts() {
        goProducts();
        Elements.HeaderAdmin.subProductsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsStats() {
        goProducts();
        Elements.HeaderAdmin.productsStatsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsOptionTypes() {
        goProducts();
        Elements.HeaderAdmin.optionTypesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsProperties() {
        goProducts();
        Elements.HeaderAdmin.propertiesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsPrototypes() {
        goProducts();
        Elements.HeaderAdmin.prototypesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsBrands() {
        goProducts();
        Elements.HeaderAdmin.brandsButtton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsProducers() {
        goProducts();
        Elements.HeaderAdmin.producersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goProductsProducersCountries() {
        goProducts();
        Elements.HeaderAdmin.producersCountriesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел Импорт и его подразделы ========

    public void goImport() {
        Elements.HeaderAdmin.importButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportQueueOfTasks() {
        goImport();
        Elements.HeaderAdmin.queueOfTasksButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportStats() {
        goImport();
        Elements.HeaderAdmin.importStatsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportArchive() {
        goImport();
        Elements.HeaderAdmin.archiveButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportBlackList() {
        goImport();
        Elements.HeaderAdmin.blackListButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportCategory() {
        goImport();
        Elements.HeaderAdmin.categoryButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportFilters() {
        goImport();
        Elements.HeaderAdmin.filtersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportProducts() {
        goImport();
        Elements.HeaderAdmin.importProductsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goImportPrice() {
        goImport();
        Elements.HeaderAdmin.priceButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел отчеты ==============

    public void goReports() {
        Elements.HeaderAdmin.reportsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел настройки ============

    public void goSettings() {
        Elements.HeaderAdmin.settingsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========= Раздел Маркетинг и его подразделы ===========

    public void goMarketing() {
        Elements.HeaderAdmin.marketingButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingPromoCards() {
        goMarketing();
        Elements.HeaderAdmin.promoCardsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingPromoAction() {
        goMarketing();
        Elements.HeaderAdmin.promoActionButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingWelcomeBanners() {
        goMarketing();
        Elements.HeaderAdmin.welcomeBannersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingAdvertisement() {
        goMarketing();
        Elements.HeaderAdmin.advertisementButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingYandexMarket() {
        goMarketing();
        Elements.HeaderAdmin.yandexMarketButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingCarts() {
        goMarketing();
        Elements.HeaderAdmin.cartsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingBonusCards() {
        goMarketing();
        Elements.HeaderAdmin.bonusCardsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingRetailersPrograms() {
        goMarketing();
        Elements.HeaderAdmin.retailersProgramsButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    public void goMarketingNewCities() {
        goMarketing();
        Elements.HeaderAdmin.newCitiesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел пользователи ===========

    public void goUsers() {
        Elements.HeaderAdmin.usersButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }

    //========== Раздел Страницы ===========

    public void goPages() {
        Elements.HeaderAdmin.pagesButton();
        printMessage("Going to " + Elements.getText() + "...");
        click(Elements.getLocator());
    }



}
