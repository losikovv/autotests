package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;
import ru.instamart.autotests.configuration.Users;


// Session helper is for handling testing sessions
    // Contains various methods for user and orders



public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

    // TODO перенести в AdminHelper
    public void getUrlAsAdmin(String targetUrlInAdminPanel) {
        getUrl(targetUrlInAdminPanel);  // пытаемся перейти по указанному URL в админку
        if (isOnSite()) {               // если не попали, то перелогиниваемся с правами администратора и идем снова
            getBaseUrl();
            if (isUserAuthorised()) {
                doLogout();
            }
            doLoginAs("admin");
            getUrl(targetUrlInAdminPanel);
        }
    }



    // ======= Registration =======

    /**
     * Perform new user registration with the given credentials
     */
    public void regNewUser(String name, String email, String password, String passwordConfirmation) {
        printMessage("Performing registration...");
        openAuthModal();
        switchToRegistrationTab();
        fillRegistrationForm(name, email, password, passwordConfirmation);
        sendForm();
        waitForIt(3);
    }

    /**
     * Perform new user registration with the credentials from the given user-data object
     */
    public void regNewUser(UserData userData) {
        printMessage("Performing registration...");
        openAuthModal();
        switchToRegistrationTab();
        fillRegistrationForm(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
        sendForm();
        waitForIt(4);
    }

    /**
     * Method returns if the given username is registered in the system or not
     */
    public boolean isUserRegistered(String username) {
        // TODO идем в админку и чекаем наличие пользователя в поиске
        return true;
    }



    // ======= Методы авторизации =======

    /** Авторизоваться тестовым пользователем с указанной ролью */
    public void doLoginAs(String role) {
        if (!isUserAuthorised()) {
            doLogin(Users.getCredentials(role));
            printMessage("Logged-in with " + role + " privileges\n");
        } else printMessage("Skip authorisation - already logged in");
    }

    /** Авторизоваться пользователем с указанными реквизитами */
    public void doLogin(String email, String password) {
        if (!isUserAuthorised()) {
            printMessage("Performing authorisation...");
            openAuthModal();
            switchToAuthorisationTab();
            fillAuthorisationForm(email, password);
            sendForm();
            waitForIt(3);
        }
    }

    /** Авторизоваться пользователем, если неавторизован */
    public void doLoginIfNeeded(UserData userData) {
        if (!isUserAuthorised()) { doLogin(userData); }
    }

    /** Авторизоваться пользователем */
    public void doLogin(UserData userData) {
        printMessage("Performing authorisation...");
        openAuthModal();
        waitForIt(1);
        switchToAuthorisationTab();
        fillAuthorisationForm(userData.getLogin(), userData.getPassword());
        sendForm();
        waitForIt(3);
    }

    /** Авторизоваться через Facebook, если неавторизован */
    public void doLoginWithFacebookIfNeeded() {
        if (!isUserAuthorised()) { doLoginWithFacebook(); }
    }

    //TODO
    /** Авторизоваться через Facebook */
    public void doLoginWithFacebook() {
    }


    /** Авторизоваться через VK, если неавторизован */
    public void doLoginWithVKIfNeeded() {
        if (!isUserAuthorised()) { doLoginWithVK(); }
    }

    //TODO
    /** Авторизоваться через VK */
    public void doLoginWithVK() {
    }

    /** Определить авторизован ли пользователь */
    public boolean isUserAuthorised() {
        printMessage("Checking authorisation...");
        if (isElementDetected(Elements.Site.Header.profileButton())) {
            printMessage("✓ Authorised\n");
            return true;
        } else {
            printMessage("No auth!\n");
            return false;
        }
    }


    // ======= De-Authorisation =======


    /** Деавторизоваться */
    public void doLogout() {
        printMessage("Log-out\n");
        if (!isInAdmin()) {
            click(Elements.Site.Header.profileButton());
            click(Elements.Site.AccountMenu.logoutButton());
        } else {
            click(Elements.Admin.Header.logoutButton());
        }
        waitForIt(2);
    }


    /** Деавторизоваться, оставшись на текущей странице */
    public void dropAuth() {
        String currentURL = currentURL();
        if (isUserAuthorised()) {
            doLogout();
            getUrl(currentURL);
        }
    }



    // ======= Handling test users =======

    // TODO перенести в Administration helper
    /**
     * Delete all test users from admin panel
     */
    public void deleteAllTestUsers() {

        final String targetPath = "admin/users?q%5Bemail_cont%5D=testuser%40example.com";
        getUrlAsAdmin(baseUrl + targetPath);

        // Delete first user if it's present in the list
        if(isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr"))) {
            deleteFirstUserInTable();
            deleteAllTestUsers(); // Keep deleting users recursively
        } else {
            printMessage("✓ Complete: no test users left\n");
        }
    }

    // TODO перенести в Administration helper
    /**
     * Delete first user in the Users table in admin panel
     */
    private void deleteFirstUserInTable() {
        String XPATH_LOGIN = "//*[@id='content']/div/table/tbody/tr[1]/td[1]/div[1]/a";
        String XPATH_DELETE = "//*[@id='content']/div/table/tbody/tr/td[3]/a[2]";
        printMessage("- delete user " + getText(By.xpath(XPATH_LOGIN)));
        click(By.xpath(XPATH_DELETE));
        handleAlert();
        waitForIt(1);
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

        final String targetPath = "admin/shipments?search%5Bemail%5D=autotestuser%40instamart.ru&search%5Bonly_completed%5D=1&search%5Bstate%5D%5B%5D=ready";
        getUrlAsAdmin(baseUrl + targetPath);

        // Cancel first order if it's present in the list
        if(!isElementPresent(By.className("no-objects-found"))) {
            cancelFirstOrderInTable();
            // Keep cancelling orders recursively
            cancelAllTestOrders();
        } else {
            printMessage("✓ Complete: no test orders left active\n");
        }
    }

    // TODO перенести в Administration helper
    /** Cancel first order in the Shipments table in admin panel */
    private void cancelFirstOrderInTable(){
        click(By.xpath("//*[@id='listing_orders']/tbody/tr/td[14]/a"));
        waitForIt(1);
        cancelOrder(); // TODO прокидывать причину отмены и текст
    }

    // TODO перенести в Administration helper
    /** Cancel order on the order page in admin panel */
    public void cancelOrder(){ // TODO Параметризовать причину отмены и текст
        printMessage("- cancel order " + currentURL());
        // click cancel button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        handleAlert();
        // choose cancellation reason
        click(By.id("cancellation_reason_id_4")); // причина - тестовый заказ
        // fill order cancellation reason form
        fillField(By.id("cancellation_reason_details"),"Тестовый заказ");
        // click confirmation button
        click(By.xpath("//*[@id='new_cancellation']/fieldset/div[3]/button"));
        waitForIt(2);
    }

    // TODO перенести в Administration helper
    public void cleanup(){
        printMessage("================= CLEANING-UP =================\n");

        printMessage("Canceling test orders...");
        cancelAllTestOrders();

        printMessage("Deleting test users...");
        deleteAllTestUsers();
    }



    // ======= Методы модалки авторизации/регистрации =======

    /** Открыть форму авторизации/регистрации */
    public void openAuthModal(){
        if (currentURL().equals(baseUrl)) click(Elements.Site.LandingPage.loginButton());
            else click(Elements.Site.Header.loginButton());

        waitForIt(1);

        if(isAuthModalOpen()) printMessage("> open auth modal");
            else printMessage(" >>> can't open auth modal");
    }

    /** Закрыть форму авторизации/регистрации */
    public void closeAuthModal(){
        click(Elements.Site.AuthModal.closeButton());
        waitForIt(1);
    }

    /** Определить открыта ли модалка авторизации/регистрации */
    public boolean isAuthModalOpen() {
        return isElementDisplayed(Elements.Site.AuthModal.popup());
    }

    /** Переключиться на вкладку авторизации */
    private void switchToAuthorisationTab(){
        printMessage("> switch to authorisation tab");
        click(Elements.Site.AuthModal.authorisationTab());
    }

    /** Заполнить поля формы авторизации */
    private void fillAuthorisationForm(String email, String password) {
        printMessage("> enter auth credentials");
        fillField(Elements.Site.AuthModal.emailField(), email);
        fillField(Elements.Site.AuthModal.passwordField(), password);
    }

    /** Переключиться на вкладку регистрации */
    private void switchToRegistrationTab(){
        printMessage("> switch to registration tab");
        click(Elements.Site.AuthModal.registrationTab());
    }

    /** Заполнить поля формы регистрации */
    private void fillRegistrationForm(String name, String email, String password, String passwordConfirmation) {
        printMessage("> enter registration credentials");
        fillField(Elements.Site.AuthModal.nameField(), name);
        fillField(Elements.Site.AuthModal.emailField(), email);
        fillField(Elements.Site.AuthModal.passwordField(), password);
        fillField(Elements.Site.AuthModal.passwordConfirmationField(), passwordConfirmation);
    }

    /** Отправить форму */
    private void sendForm(){
        printMessage("> send form\n");
        click(Elements.Site.AuthModal.submitButton());
    }

    /** Перейти в форму восстановления пароля */
    private void proceedToPasswordRecovery(){
        printMessage("> proceed to password recovery");
        click(Elements.Site.AuthModal.forgotPasswordButton());
    }

    /** Запросить восстановление пароля */
    public void recoverPassword(String email){
        openAuthModal();
        switchToAuthorisationTab();
        proceedToPasswordRecovery();
        printMessage("> recovery for " + email);
        fillField(Elements.Site.AuthModal.emailField(),email);
        sendForm();
        waitForIt(1);
    }

    /** Определить отправлена ли форма восстановления пароля */
    public boolean isRecoverySent(){
        if (!isElementDisplayed(Elements.Site.AuthModal.popup())
                || isElementPresent(Elements.Site.AuthModal.emailField())) {
                    printMessage("Recovery not sent!");
                    return false;
                } else {
            printMessage("✓ Recovery requested");
            return true;
        }
    }



    // ======= Методы модалки авторизации/регистрации =======

    public void openAccountMenu() {
        if(!isAccountMenuOpen()) {
        click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already opened");
    }

    public void closeAccountMenu() {
        if(isAccountMenuOpen()) {
        click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already closed");
    }

    public boolean isAccountMenuOpen() {
        return isElementDisplayed(Elements.Site.AccountMenu.popup());
    }

}
