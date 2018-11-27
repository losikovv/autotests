package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.UserData;

public class PerformHelper extends HelperBase {

    private ApplicationManager kraken;

    PerformHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Кликнуть элемент по локатору */
    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n){
            printMessage("Can't click element <" + locator + "> on " + fetchCurrentURL() + "\n");
        }
    }

    /** Кликнуть элемент */
    public void click(Elements element) {
        try {
            driver.findElement(Elements.getLocator()).click();
        }
        catch (NoSuchElementException n) {
            if(Elements.getText() == null) {
                printMessage("Can't click element <" + Elements.getLocator()
                        + ">\nNo such element on " + fetchCurrentURL() + "\n");
            } else {
                printMessage("Can't click element " + Elements.getText() + " <" + Elements.getLocator()
                        + ">\nNo such element on " + fetchCurrentURL() + "\n");
            }
        }
        catch (ElementNotVisibleException v) {
            if(Elements.getText() == null) {
                printMessage("Can't click element <" + Elements.getLocator()
                        + ">\nElement is not visible on " + fetchCurrentURL() + "\n");
            } else {
                printMessage("Can't click element " + Elements.getText() + " <" + Elements.getLocator()
                        + ">\nElement is not visible on " + fetchCurrentURL() + "\n");
            }
        }
    }

    /** Заполнить поле указанным текстом */
    public void fillField(Elements element, String text) {
        click(element);
        if (text != null) {
            String existingText = driver.findElement(Elements.getLocator()).getAttribute("value");
            if (!text.equals(existingText)) {
                driver.findElement(Elements.getLocator()).clear();
                driver.findElement(Elements.getLocator()).sendKeys(text);
            }
        }
    }

    /** Заполнить поле по локатору указанным текстом */
    public void fillField(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = driver.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    /** Открыть меню аккаунта */
    public void openAccountMenu() {
        if(!kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already opened");
    }

    /** Закрыть меню аккаунта */
    public void closeAccountMenu() {
        if(kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already closed");
    }


    // ======= Методы регистрации =======

    /** Зарегистрироваться с реквизитами из переданного объекта UserData */
    public void registration(UserData userData) throws Exception {
        registration(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
    }

    /** Зарегистрироваться с указанными реквизитами */
    public void registration(String name, String email, String password, String passwordConfirmation) throws Exception {
        printMessage("Performing registration...");
        openAuthModal();
        regSequence(name,email,password,passwordConfirmation);
        waitingFor(3);
    }

    /** Регистрационная последовательность с реквизитами из переданного объекта UserData */
    public void regSequence(UserData userData) throws Exception {
        regSequence(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
    }

    /** Регистрационная последовательность с указанными реквизитами */
    private void regSequence(String name, String email, String password, String passwordConfirmation) throws Exception {
        switchToRegistrationTab();
        fillRegistrationForm(name, email, password, passwordConfirmation);
        sendForm();
    }


    // ======= Методы авторизации =======

    public void reachAdmin(Pages page) throws Exception {
        kraken.get().url(fullBaseUrl + Pages.getPagePath());        // пытаемся перейти по указанному URL в админку
        if (kraken.detect().isOnSite()) {                           // если не попали, то перелогиниваемся с правами администратора и идем снова
            kraken.get().baseUrl();
            if (kraken.detect().isUserAuthorised()) {
                logout();
            }
            loginAs("admin");
            kraken.get().url(fullBaseUrl + Pages.getPagePath());
        }
    }


    /** Залогиниться юзером с указанной ролью */ //TODO добавить сеттер as(String role), проверки на авторизованность вынести в метод login
    public void loginAs(String role) throws Exception {
        if (!kraken.detect().isUserAuthorised()) {
            login(Users.getCredentials(role));
            printMessage("Logged-in with " + role + " privileges\n");
        } else {
            printMessage("Skip authorisation, already logged-in");
        }
    }


    /** Залогиниться с реквизитами из переданного объекта UserData */
    private void login(UserData userData) throws Exception {
        login(userData.getLogin(), userData.getPassword());
    }


    /** Залогиниться с указанными реквизитами */
    public void login(String email, String password) throws Exception {
        printMessage("Performing authorisation...");
        openAuthModal();
        waitingFor(1);
        authSequence(email, password);
        waitingFor(2);
    }

    /** Авторизационная последовательность для юзера с указанной ролью */
    public void authSequence(String role) throws Exception {
        authSequence(Users.getCredentials(role));
    }


    /** Авторизационная последовательность с реквизитами из переданного объекта UserData */
    private void authSequence(UserData userData) throws Exception {
        authSequence(userData.getLogin(), userData.getPassword());
    }


    /** Авторизационная последовательность с указанными реквизитами */
    private void authSequence(String email, String password) throws Exception {
        switchToAuthorisationTab();
        fillAuthorisationForm(email, password);
        sendForm();
    }


    // ======= De-Authorisation =======

    /** Деавторизоваться */
    public void logout() {
        printMessage("Log-out\n");
        if (!kraken.detect().isInAdmin()) {
            kraken.perform().click(Elements.Site.Header.profileButton());
            kraken.perform().click(Elements.Site.AccountMenu.logoutButton());
        } else {
            kraken.perform().click(Elements.Admin.Header.logoutButton());
        }
        waitingFor(1);
    }


    /** Деавторизоваться, оставшись на текущей странице */
    public void dropAuth() {
        String currentURL = fetchCurrentURL();
        if (kraken.detect().isUserAuthorised()) {
            kraken.perform().logout();
            kraken.get().url(currentURL);
        }
    }


    // ======= Методы модалки авторизации/регистрации =======

    /** Открыть форму авторизации/регистрации */
    public void openAuthModal(){
        if (kraken.detect().isOnLanding()) {
            kraken.perform().click(Elements.Site.LandingPage.loginButton());
        } else {
            kraken.perform().click(Elements.Site.Header.loginButton());
        }

        waitingFor(1);

        if(kraken.detect().isAuthModalOpen()) {
            printMessage("> open auth modal");
        } else {
            printMessage(" Can't open auth modal");
        }
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

    /** Закрыть форму авторизации/регистрации */
    public void closeAuthModal(){
        kraken.perform().click(Elements.Site.AuthModal.closeButton());
        waitingFor(1);
    }


    /** Переключиться на вкладку авторизации */
    private void switchToAuthorisationTab() throws Exception {
        try {
            kraken.perform().printMessage("> switch to authorisation tab");
            kraken.perform().click(Elements.Site.AuthModal.authorisationTab());
        } catch (ElementNotInteractableException e) { // TODO попробовать перенести кетч в методы click в HelperBase
            kraken.perform().printMessage(" > have some troubles, waiting and trying again...");
            kraken.perform().click(Elements.Site.AuthModal.authorisationTab());
        }
    }


    /** Заполнить поля формы авторизации */
    private void fillAuthorisationForm(String email, String password) {
        printMessage("> enter auth credentials");
        kraken.perform().fillField(Elements.Site.AuthModal.emailField(), email);
        kraken.perform().fillField(Elements.Site.AuthModal.passwordField(), password);
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
        waitingFor(1);
    }

}
