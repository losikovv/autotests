package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.UserData;



    // Session helper is for handling testing sessions
    // Contains various methods for user and orders



public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver, EnvironmentData environment) {
        super(driver, environment);
    }

    // TODO перенести в AdminHelper
    public void getUrlAsAdmin(String targetUrl) {
        // trying to get target URL in admin panel
        getUrl(targetUrl);
        // if we don't have admin privileges then log-in as admin and try again
        if (!itsInAdmin()) {
            getBaseUrl();
            if (isUserAuthorised()) {
                doLogout();
            }
            doLoginAsAdmin();
            getUrl(targetUrl);
        }
    }



    // ======= Registration =======

    /**
     * Perform new user registration with the given credentials
     */
    public void regNewUser(String name, String email, String password, String passwordConfirmation) {
        openLoginForm();
        switchToRegistration();
        fillRegistrationForm(name, email, password, passwordConfirmation);
        sendForm();
        waitForIt();
        printMessage("Registration has been performed\n");
    }

    /**
     * Perform new user registration with the credentials from the given user-data object
     */
    public void regNewUser(UserData userData) {
        openLoginForm();
        switchToRegistration();
        fillRegistrationForm(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
        sendForm();
        waitForIt();
        printMessage("Registration has been performed\n");
    }

    /**
     * Method returns if the given username is registered in the system or not
     */
    public boolean isUserRegistered(String username) {
        // TODO идем в админку и чекаем наличие пользователя в поиске
        return true;
    }



    // ======= Password recovery =======

    public void recoverPassword(String email){
        openLoginForm();
        switchToAuthorisation();
        clickRecovery();
        fillField(By.name("spree_user[email]"), email);
        sendRecoveryForm();
        waitForIt();
    }

    public boolean isRecoverySent(){
        return !isElementDisplayed(By.name("spree_user[email]"));
    }



    // ======= Authorisation =======

    /**
     * Perform log-in with the given user credentials
     */
    public void doLogin(String email, String password) {
        printMessage("Performing authorisation...");
        openLoginForm();
        switchToAuthorisation();
        fillAuthorisationForm(email, password);
        sendForm();
        waitForIt();
    }

    /**
     * Perform log-in with the user credentials from the given user-data object
     */
    public void doLogin(UserData userData) {
        printMessage("Performing authorisation...");
        openLoginForm();
        switchToAuthorisation();
        fillAuthorisationForm(userData.getLogin(), userData.getPassword());
        sendForm();
        waitForIt();
    }

    /**
     * Проверяем авторизованность по наличию на странице кнопки "Профиль"
     */
    public boolean isUserAuthorised() {
        String XPATH = "//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[1]/div[1]";
        String TEXT = "Профиль";
        printMessage("Checking authorisation...");
        if (isElementPresent(By.xpath(XPATH)) && getText(By.xpath(XPATH)).equals(TEXT)) {
            printMessage("Success!\n");
            return true;
        } else {
            printMessage("No auth!\n");
            return false;
        }
    }



    // ======= De-Authorisation =======

    /**
     * Общий метод логаута для сайта и админки
     */
    public void doLogout() {
        if (!itsInAdmin()) {
            doLogoutFromSite();
        } else {
            doLogoutFromAdmin();
        }
    }

    /**
     * Делаем логаут на сайте
     */
    private void doLogoutFromSite() {
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div")); //жмем "Профиль"
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[8]/a")); //жмем "Выйти"
        waitForIt();
        printMessage("Logged-out from site\n");
    }

    /**
     * Делаем логаут в админке
     */
    private void doLogoutFromAdmin() {
        click(By.xpath("//*[@id='login-nav']/li[3]/a")); //клик по кнопке Выйти
        waitForIt();
        printMessage("Logged-out from admin\n");
    }

    /**
     * Drop authorisation and return to current page
     */
    public void dropAuth() {
        String currentURL = driver.getCurrentUrl();
        if (isUserAuthorised()) {
            doLogout();
            getUrl(currentURL);
        }
    }



    // ======= Handling test users =======

    // TODO перенести в UserData
    /**
     * Do log-in with user credentials of autotest@instamart.ru which is reserved for autotests and have admin privileges
     */
    public void doLoginAsAdmin() {
        final String LOGIN = "autotestuser@instamart.ru";
        final String PASSWORD = "DyDrasLipMeibe7";
        doLogin(new UserData(LOGIN, PASSWORD, null));
        printMessage("Logged-in with admin privileges as " + LOGIN + "\n");
    }

    // TODO перенести в UserData
    /**
     * Do log-in with Facebook as reserved for testing needs user
     */
    public void doLoginWithFacebook() {
        //TODO
    }

    // TODO перенести в UserData
    /**
     * Do log-in with VKontakte as reserved for testing needs user
     */
    public void doLoginWithVKontakte() {
        //TODO
    }


    // TODO перенести в Administration helper
    /**
     * Delete all test users from admin panel
     */
    public void deleteAllTestUsers() {

        //TODO переделать на
        //TODO asAdmin
        //TODO getUrl

        // Getting target URL in admin panel which contains table with test users only
        getUrlAsAdmin(baseUrl + "admin/users?q%5Bemail_cont%5D=testuser%40example.com");
        // Delete first user if it's present in the list
        if(isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr"))) {
            deleteFirstUserInTable();
            printMessage("Test user has been deleted\n"); //TODO добавить вывод логина пользователя, брать со страницы
            // Keep deleting users recursively
            deleteAllTestUsers();
        } else {
            printMessage("TEST USER DELETION IS COMPLETE\n");
        }
    }

    // TODO перенести в Administration helper
    /**
     * Delete first user in the Users table in admin panel
     */
    private void deleteFirstUserInTable() {
        printMessage("Deleting user on " + currentURL());
        click(By.xpath("//*[@id='content']/div/table/tbody/tr/td[3]/a[2]"));
        closeAlertAndGetItsText();
        waitForIt();
    }



    // ======= Handling test orders =======

    // TODO перенести в Administration helper
    /**
     * Cancel all test orders from admin panel
     */
    public void cancelAllTestOrders() {

        // фильтр по частичному совпадению email пока не работает,
        // поэтому тестовые заказы пока делаем с autotestuser@instamart.ru
        // позже нужно переделать под юзеров @example.com

        // Getting target URL in admin panel which contains table with test orders only
        getUrlAsAdmin(baseUrl + "admin/shipments?search%5Bemail%5D=autotestuser%40instamart.ru&search%5Bonly_completed%5D=1&search%5Bstate%5D%5B%5D=ready");

        // Cancel first order if it's present in the list
        if(!isElementPresent(By.className("no-objects-found"))) {
            cancelFirstOrderInTable();
            printMessage("Test order has been canceled\n");
            // Keep cancelling orders recursively
            cancelAllTestOrders();
        } else {
            printMessage("TEST ORDERS CANCELLATION IS COMPLETE\n");
        }
    }

    // TODO перенести в Administration helper
    /**
     * Cancel first order in the Shipments table in admin panel
     */
    // TODO прокидывать причину отмены и текст
    private void cancelFirstOrderInTable(){
        // Go to the first order in table
        click(By.xpath("//*[@id='listing_orders']/tbody/tr/td[14]/a"));
        waitForIt();
        // Perform cancellation
        cancelOrder();
    }

    // TODO перенести в Administration helper
    /**
     * Cancel order with the given number
     */
    // TODO прокидывать причину отмены и текст
    public void cancelOrder(String orderNumber){
        // get order page in admin panel
        getUrlAsAdmin(baseUrl + "admin/orders/" + orderNumber + "/edit");
        cancelOrder();
    }

    // TODO перенести в Administration helper
    /**
     * Cancel order on the order page in admin panel
     */
    // TODO Параметризовать причину отмены и текст
    public void cancelOrder(){
        printMessage("Cancelling order on " + currentURL());
        // click cancel button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order cancellation alert
        closeAlertAndGetItsText();
        // choose cancellation reason
        click(By.id("cancellation_reason_id_4")); // причина - тестовый заказ
        // fill order cancellation reason form
        fillField(By.id("cancellation_reason_details"),"Тестовый заказ");
        // click confirmation button
        click(By.xpath("//*[@id='new_cancellation']/fieldset/div[3]/button"));
        waitForIt();
    }

    // TODO перенести в Administration helper
    /**
     * Resume order with the given number
     */
    public void resumeOrder(String orderNumber){
        // get order page in admin panel
        getUrlAsAdmin(baseUrl + "admin/orders/" + orderNumber + "/edit");
        resumeOrder();
    }

    // TODO перенести в Administration helper
    /**
     * Resume order on the order page in admin panel
     */
    public void resumeOrder(){
        // click resume button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order resume alert
        closeAlertAndGetItsText();
        waitForIt();
    }

    // TODO перенести в Administration helper
    /**
     * Find out if the order with the given order number is canceled
     */
    public boolean isOrderCanceled(String orderNumber) {
        // get order page in admin panel
        getUrlAsAdmin(baseUrl + "admin/orders/" + orderNumber + "/edit");
        return isOrderCanceled();
    }

    // TODO перенести в Administration helper
    /**
     * Find out if the order is canceled by checking the order page in admin panel
     */
    public boolean isOrderCanceled() {
        String XPATH = "//*[@id='content']/div/table/tbody/tr[3]/td/b";
        if (isElementPresent(By.xpath(XPATH))){
            return getText(By.xpath(XPATH)).equals("ЗАКАЗ ОТМЕНЕН");
        } else {
            return false;
        }
    }

    // TODO перенести в Administration helper
    public void cleanup(){
        printMessage("================= CLEANING-UP =================\n");

        printMessage("Canceling test orders");
        cancelAllTestOrders();

        printMessage("Deleting test users");
        deleteAllTestUsers();
    }



    // ======= Methods for log-in form =======

    /**
     * Open the log-in form by clicking the "Log-in" button
     */
    private void openLoginForm(){
        if (itsOnLandingPage()){
            click(By.xpath("/html/body/div[4]/header/div[2]/ul/li[3]"));
        } else {
            click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]"));
        }
        if(isElementDisplayed(By.id("auth"))) {
            printMessage("Login form opened");
        } else {
            waitForIt();
            printMessage("Can't open login form");
        }
    }

    private void switchToAuthorisation(){
        click(By.xpath("//*[@id='auth']/div/div/div[1]/div/button[1]"));
    }

    private void fillAuthorisationForm(String email, String password) {
        printMessage("Entering auth credentials");
        fillField(By.name("spree_user[email]"), email);
        fillField(By.name("spree_user[password]"), password);
    }

    private void switchToRegistration(){
        click(By.xpath("//*[@id='auth']/div/div/div[1]/div/button[2]"));
    }

    private void fillRegistrationForm(String name, String email, String password, String passwordConfirmation) {
        printMessage("Entering reg credentials");
        fillField(By.name("spree_user[fullname]"), name);
        fillField(By.name("spree_user[email]"), email);
        fillField(By.name("spree_user[password]"), password);
        fillField(By.name("spree_user[password_confirmation]"), passwordConfirmation);
    }

    private void sendForm(){
        printMessage("Sending form");
        click(By.xpath("//*[@id='auth']/div/div/div[1]/form/div/button"));
    }

    /**
     * Click "Forgot password?" button in the authorisation form
     */
    private void clickRecovery(){
        click(By.xpath("//*[@id='auth']/div/div/div[1]/form/div/div[3]/div[2]"));
    }

    private void sendRecoveryForm(){
        printMessage("Sending form");
        click(By.xpath("//*[@id='auth']/div/div/div[2]/form/div/button"));
    }

}
