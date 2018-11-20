package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Pages;
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
    public void reachAdmin(String url) throws Exception {
        getUrl(url);                    // пытаемся перейти по указанному URL в админку
        if (isOnSite()) {               // если не попали, то перелогиниваемся с правами администратора и идем снова
            getBaseUrl();
            if (isUserAuthorised()) {
                doLogout();
            }
            doLoginAs("admin");
            getUrl(url);
        }
    }

    public void reachAdmin(Pages page) throws Exception {
        getUrl(fullBaseUrl + Pages.getPagePath());  // пытаемся перейти по указанному URL в админку
        if (isOnSite()) {                           // если не попали, то перелогиниваемся с правами администратора и идем снова
            getBaseUrl();
            if (isUserAuthorised()) {
                doLogout();
            }
            doLoginAs("admin");
            getUrl(fullBaseUrl + Pages.getPagePath());
        }
    }



    // ======= Методы регистрации =======

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

    public void doLoginAs(String role) throws Exception {
        if (!isUserAuthorised()) {
            doLogin(Users.getCredentials(role));
            printMessage("Logged-in with " + role + " privileges\n");
        } else {
            printMessage("Skip authorisation, already logged-in");
        }
    }

    private void doLogin(UserData userData) throws Exception {
        doLogin(userData.getLogin(), userData.getPassword());
    }

    public void doLogin(String email, String password) throws Exception {
        printMessage("Performing authorisation...");
        openAuthModal();
        waitForIt(1);
        performAuthSequence(email, password);
        waitForIt(2);
    }

    public void performAuthSequence(String role) throws Exception {
        performAuthSequence(Users.getCredentials(role));
    }

    private void performAuthSequence(UserData userData) throws Exception {
        performAuthSequence(userData.getLogin(), userData.getPassword());
    }

    private void performAuthSequence(String email, String password) throws Exception {
        switchToAuthorisationTab();
        fillAuthorisationForm(email, password);
        sendForm();
    }


    /** Авторизоваться через соцсети */
    public void doLoginWith(String provider) {
        if (!isUserAuthorised()) {
            switch (provider) {
                case "facebook":
                    //TODO
                case ("vkontakte"):
                    //TODO
            }
        }
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

    /** Delete all users on a given page in admin panel */
    public void deleteUsers(Pages usersList) throws Exception {
        reachAdmin(usersList);
        if(isElementPresent(Elements.Admin.Users.userlistFirstRow())) {
            printMessage("- delete user " + getText(Elements.Admin.Users.firstUserLogin()));
            click(Elements.Admin.Users.firstUserDeleteButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
            handleAlert();
            waitForIt(1);
            deleteUsers(usersList); // Keep deleting users, recursively
        } else {
            printMessage("✓ Complete: no test users left\n");
        }
    }



    // ======= Handling test orders =======

    public void cancelOrders(Pages ordersList) throws Exception {
        reachAdmin(ordersList);
        if(!isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder()))  {
            click(Elements.Admin.Shipments.firstOrderInTable());
            waitForIt(1);
            cancelOrder(); // todo обернуть в проверку, выполнять только если тестовый заказ
            cancelOrders(ordersList); // Keep cancelling orders, recursively
        } else {
            printMessage("✓ Complete: no test orders left active\n");
        }
    }

    /** Cancel order on the order page in admin panel */
    public void cancelOrder(){
        printMessage("> cancel order " + currentURL());
        click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(4,"Тестовый заказ");
        click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        waitForIt(2);
    }

    public void cancelOrder(int reason, String details){
        printMessage("> cancel order " + currentURL());
        click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(reason,details);
        click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        waitForIt(2);
    }

    private void chooseCancellationReason(int reason, String details) {
        click(By.id("cancellation_reason_id_" + reason));               // todo вынести в elements
        fillField(By.id("cancellation_reason_details"),details);        // todo вынести в elements
    }


    // TODO перенести в Administration helper
    public void cleanup() throws Exception {
        printMessage("================= CLEANING-UP =================\n");

        printMessage("Canceling test orders...");
        cancelOrders(Pages.Admin.Shipments.testOrdersList());

        printMessage("Deleting test users...");
        deleteUsers(Pages.Admin.Users.testUsersList());
    }



    // ======= Методы модалки авторизации/регистрации =======


    /** Открыть форму авторизации/регистрации */
    public void openAuthModal(){
        if (isOnLanding()) click(Elements.Site.LandingPage.loginButton());
            else click(Elements.Site.Header.loginButton());
        waitForIt(1);

        if(isAuthModalOpen()) printMessage("> open auth modal");
            else printMessage(" Can't open auth modal");
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
    private void switchToAuthorisationTab() throws Exception {
        try {
            printMessage("> switch to authorisation tab");
            click(Elements.Site.AuthModal.authorisationTab());
        } catch (ElementNotInteractableException e) { // TODO попробовать перенести кетч в методы click в HelperBase
            printMessage(" > have some troubles, waiting and trying again...");
            click(Elements.Site.AuthModal.authorisationTab());
        }
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
    public void recoverPassword(String email) throws Exception {
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

}
