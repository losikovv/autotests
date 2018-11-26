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

    private ApplicationManager kraken;

    SessionHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }


    public void reachAdmin(Pages page) throws Exception {
        kraken.get().url(fullBaseUrl + Pages.getPagePath());        // пытаемся перейти по указанному URL в админку
        if (kraken.detect().isOnSite()) {                           // если не попали, то перелогиниваемся с правами администратора и идем снова
            kraken.get().baseUrl();
            if (isUserAuthorised()) {
                doLogout();
            }
            doLoginAs("admin");
            kraken.get().url(fullBaseUrl + Pages.getPagePath());
        }
    }




    // ======= Методы регистрации =======


    /** Зарегистрироваться с реквизитами из переданного объекта UserData */
    public void regNewUser(UserData userData) throws Exception {
        regNewUser(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
    }


    /** Зарегистрироваться с указанными реквизитами */
    public void regNewUser(String name, String email, String password, String passwordConfirmation) throws Exception {
        printMessage("Performing registration...");
        openAuthModal();
        performRegSequence(name,email,password,passwordConfirmation);
        waitFor(3);
    }


    /** Регистрационная последовательность с реквизитами из переданного объекта UserData */
    public void performRegSequence(UserData userData) throws Exception {
        performRegSequence(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
    }


    /** Регистрационная последовательность с указанными реквизитами */
    private void performRegSequence(String name, String email, String password, String passwordConfirmation) throws Exception {
        switchToRegistrationTab();
        fillRegistrationForm(name, email, password, passwordConfirmation);
        sendForm();
    }




    // ======= Методы авторизации =======


    /** Залогиниться юзером с указанной ролью */
    public void doLoginAs(String role) throws Exception {
        if (!isUserAuthorised()) {
            doLogin(Users.getCredentials(role));
            printMessage("Logged-in with " + role + " privileges\n");
        } else {
            printMessage("Skip authorisation, already logged-in");
        }
    }


    /** Залогиниться с реквизитами из переданного объекта UserData */
    private void doLogin(UserData userData) throws Exception {
        doLogin(userData.getLogin(), userData.getPassword());
    }


    /** Залогиниться с указанными реквизитами */
    public void doLogin(String email, String password) throws Exception {
        printMessage("Performing authorisation...");
        openAuthModal();
        waitFor(1);
        performAuthSequence(email, password);
        waitFor(2);
    }

    /** Авторизационная последовательность для юзера с указанной ролью */
    public void performAuthSequence(String role) throws Exception {
        performAuthSequence(Users.getCredentials(role));
    }


    /** Авторизационная последовательность с реквизитами из переданного объекта UserData */
    private void performAuthSequence(UserData userData) throws Exception {
        performAuthSequence(userData.getLogin(), userData.getPassword());
    }


    /** Авторизационная последовательность с указанными реквизитами */
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
        if (!kraken.detect().isInAdmin()) {
            kraken.perform().click(Elements.Site.Header.profileButton());
            kraken.perform().click(Elements.Site.AccountMenu.logoutButton());
        } else {
            kraken.perform().click(Elements.Admin.Header.logoutButton());
        }
        waitFor(1);
    }


    /** Деавторизоваться, оставшись на текущей странице */
    public void dropAuth() {
        String currentURL = fetchCurrentURL();
        if (isUserAuthorised()) {
            doLogout();
            kraken.get().url(currentURL);
        }
    }




    // ======= Handling test users =======


    /** Delete all users on a given page in admin panel */
    public void deleteUsers(Pages usersList) throws Exception {
        reachAdmin(usersList);
        if(isElementPresent(Elements.Admin.Users.userlistFirstRow())) {
            printMessage("- delete user " + fetchText(Elements.Admin.Users.firstUserLogin()));
            kraken.perform().click(Elements.Admin.Users.firstUserDeleteButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
            handleAlert();
            waitFor(1);
            deleteUsers(usersList); // Keep deleting users, recursively
        } else {
            printMessage("✓ Complete: no test users left\n");
        }
    }




    // ======= Handling test orders =======


    public void cancelOrders(Pages ordersList) throws Exception {
        reachAdmin(ordersList);
        if(!isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder()))  {
            kraken.perform().click(Elements.Admin.Shipments.firstOrderInTable());
            waitFor(1);
            cancelOrder(); // todo обернуть в проверку, выполнять только если тестовый заказ
            cancelOrders(ordersList); // Keep cancelling orders, recursively
        } else {
            printMessage("✓ Complete: no test orders left active\n");
        }
    }


    /** Cancel order on the order page in admin panel */
    public void cancelOrder(){
        printMessage("> cancel order " + fetchCurrentURL());
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(4,"Тестовый заказ");
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        waitFor(2);
    }


    public void cancelOrder(int reason, String details){
        printMessage("> cancel order " + fetchCurrentURL());
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(reason,details);
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        waitFor(2);
    }


    private void chooseCancellationReason(int reason, String details) {
        kraken.perform().click(By.id("cancellation_reason_id_" + reason));               // todo вынести в elements
        kraken.perform().fillField(By.id("cancellation_reason_details"),details);        // todo вынести в elements
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
        if (kraken.detect().isOnLanding()) {
            kraken.perform().click(Elements.Site.LandingPage.loginButton());
        } else {
            kraken.perform().click(Elements.Site.Header.loginButton());
        }
        waitFor(1);

        if(isAuthModalOpen()) printMessage("> open auth modal");
            else printMessage(" Can't open auth modal");
    }


    /** Закрыть форму авторизации/регистрации */
    public void closeAuthModal(){
        kraken.perform().click(Elements.Site.AuthModal.closeButton());
        waitFor(1);
    }


    /** Определить открыта ли модалка авторизации/регистрации */
    public boolean isAuthModalOpen() {
        return isElementDisplayed(Elements.Site.AuthModal.popup());
    }


    /** Переключиться на вкладку авторизации */
    private void switchToAuthorisationTab() throws Exception {
        try {
            printMessage("> switch to authorisation tab");
            kraken.perform().click(Elements.Site.AuthModal.authorisationTab());
        } catch (ElementNotInteractableException e) { // TODO попробовать перенести кетч в методы click в HelperBase
            printMessage(" > have some troubles, waiting and trying again...");
            kraken.perform().click(Elements.Site.AuthModal.authorisationTab());
        }
    }


    /** Заполнить поля формы авторизации */
    private void fillAuthorisationForm(String email, String password) {
        printMessage("> enter auth credentials");
        kraken.perform().fillField(Elements.Site.AuthModal.emailField(), email);
        kraken.perform().fillField(Elements.Site.AuthModal.passwordField(), password);
    }


    /** Переключиться на вкладку регистрации */
    private void switchToRegistrationTab(){
        printMessage("> switch to registration tab");
        kraken.perform().click(Elements.Site.AuthModal.registrationTab());
    }


    /** Заполнить поля формы регистрации */
    private void fillRegistrationForm(String name, String email, String password, String passwordConfirmation) {
        printMessage("> enter registration credentials");
        kraken.perform().fillField(Elements.Site.AuthModal.nameField(), name);
        kraken.perform().fillField(Elements.Site.AuthModal.emailField(), email);
        kraken.perform().fillField(Elements.Site.AuthModal.passwordField(), password);
        kraken.perform().fillField(Elements.Site.AuthModal.passwordConfirmationField(), passwordConfirmation);
    }


    /** Отправить форму */
    private void sendForm(){
        printMessage("> send form\n");
        kraken.perform().click(Elements.Site.AuthModal.submitButton());
    }


    /** Перейти в форму восстановления пароля */
    private void proceedToPasswordRecovery(){
        printMessage("> proceed to password recovery");
        kraken.perform().click(Elements.Site.AuthModal.forgotPasswordButton());
    }


    /** Запросить восстановление пароля */
    public void recoverPassword(String email) throws Exception {
        openAuthModal();
        switchToAuthorisationTab();
        proceedToPasswordRecovery();
        printMessage("> recovery for " + email);
        kraken.perform().fillField(Elements.Site.AuthModal.emailField(),email);
        sendForm();
        waitFor(1);
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