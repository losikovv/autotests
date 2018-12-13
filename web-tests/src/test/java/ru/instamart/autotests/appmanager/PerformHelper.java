package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.application.*;
import ru.instamart.autotests.models.UserData;

import static ru.instamart.autotests.application.Pages.*;

public class PerformHelper extends HelperBase {

    private ApplicationManager kraken;

    PerformHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    // ======= Базовые действия =======

    /** Кликнуть элемент */
    public void click(Elements element) {
        click(Elements.locator());
    }

    /** Кликнуть элемент по локатору */
    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n) {
            printMessage("Не возможно нажать на элемент <" + locator
                    + ">\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
        }
        catch (ElementNotVisibleException v) {
            printMessage("Не возможно нажать на элемент <" + locator
                    + ">\nЭлемент не отображается на " + kraken.grab().currentURL() + "\n");
        }
    }

    /** Заполнить поле указанным текстом */
    public void fillField(Elements element, String text) {
        fillField(Elements.locator(),text);
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

    /** Переключиться на фреймами по имени или id */
    void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    /** Переключиться на активный элемент */
    void switchToActiveElement() {
        driver.switchTo().activeElement();
    }

    /** Переключиться на дефолтный контент */
    void switchToDefaultContent() {
        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
    }

    /** Ожидание равное переданному значению умноженному на переменную 'implicitlyWait' */
    public void waitingFor(int duration){
        for (int i = 1; i <= duration; i++){
            kraken.detect().isElementPresent(By.xpath("//*[@id='nowhere']"));
        }
    }


    // ======= Меню аккаунта =======

    /** Открыть меню аккаунта */
    public void openAccountMenu() {
        if(!kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Пропускаем открытие меню аккаунта, оно уже открыто");
    }

    /** Закрыть меню аккаунта */
    public void closeAccountMenu() {
        if(kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Пропускаем закрытие меню аккаунта, оно уже закрыто");
    }


    // ======= Регистрация =======

    /** Зарегистрироваться с реквизитами из переданного объекта UserData */
    public void registration(UserData userData) throws Exception {
        registration(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
    }

    /** Зарегистрироваться с указанными реквизитами */
    public void registration(String name, String email, String password, String passwordConfirmation) throws Exception {
        printMessage("Регистрируемся...");
        openAuthModal();
        regSequence(name,email,password,passwordConfirmation);
        waitingFor(3); // Ожидание раздизебливания кнопки подтверждения регистрации
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


    // ======= Авторизация / деавторизация =======

    /** Залогиниться юзером с указанной ролью */
    public void loginAs(String role) throws Exception {
        String startURL = kraken.grab().currentURL();
        if (!startURL.equals(fullBaseUrl) && kraken.detect().isUserAuthorised()) {
            kraken.get().profilePage();
            if (!kraken.grab().text(Elements.Site.AccountPage.email()).equals(Users.getCredentials(role).getLogin())) {
                logout();
            }
            kraken.get().url(startURL);
        }
        login(Users.getCredentials(role));
        printMessage("Авторизован с правами " + role + "\n");
    }

    /** Залогиниться с реквизитами из переданного объекта UserData */
    public void login(UserData userData) throws Exception {
        login(userData.getLogin(), userData.getPassword());
    }

    /** Залогиниться с указанными реквизитами */
    public void login(String email, String password) throws Exception {
        if (!kraken.detect().isUserAuthorised()) {
            printMessage("Авторизуемся...");
            openAuthModal();
            waitingFor(1); // Ожидание загрузки модалки авторизации
            authSequence(email, password);
            waitingFor(2); // Ожидание раздизебливания кнопки подтверждения авторизации
        } else {
            printMessage("Пропускаем авторизацию, уже авторизованы");
        }
    }

    /** Авторизационная последовательность для юзера с указанной ролью */
    public void authSequence(String role) throws Exception {
        authSequence(Users.getCredentials(role));
    }

    /** Авторизационная последовательность с реквизитами из переданного объекта UserData */
    public void authSequence(UserData userData) throws Exception {
        authSequence(userData.getLogin(), userData.getPassword());
    }

    /** Авторизационная последовательность с указанными реквизитами */
    public void authSequence(String email, String password) throws Exception {
        switchToAuthorisationTab();
        fillAuthorisationForm(email, password);
        sendForm();
    }

    /** Деавторизоваться */
    public void logout() {
        if (!kraken.detect().isInAdmin()) {
            kraken.perform().click(Elements.Site.Header.profileButton());
            kraken.perform().click(Elements.Site.AccountMenu.logoutButton());
        } else {
            kraken.perform().click(Elements.Admin.Header.logoutButton());
        }
        waitingFor(1); // Ожидание загрузки лендинга после логаута
        if(kraken.detect().isOnLanding()) {
            printMessage("Логаут\n");
        }
    }

    /** Деавторизоваться быстро по прямой ссылке */
    public void quickLogout() {
        kraken.get().page("logout");
        waitingFor(1); // Ожидание деавторизации
    }


    // ======= Методы модалки авторизации/регистрации =======

    /** Открыть форму авторизации/регистрации */
    public void openAuthModal(){
        if (kraken.detect().isAuthModalOpen()) {
            printMessage("> модалка авторизации открыта");
        } else {
            if (kraken.detect().isOnLanding()) {
                kraken.perform().click(Elements.Site.Landing.loginButton());
            } else {
                kraken.perform().click(Elements.Site.Header.loginButton());
            }
        }
        printMessage("> открываем модалку авторизации");
        waitingFor(1); // Ожидание открытия модалки авторизации
    }

    /** Переключиться на вкладку регистрации */
    private void switchToRegistrationTab(){
        printMessage("> переключаемся на вкладку регистрации");
        kraken.perform().click(Elements.Site.AuthModal.registrationTab());
    }

    /** Заполнить поля формы регистрации */
    private void fillRegistrationForm(String name, String email, String password, String passwordConfirmation) {
        printMessage("> заполняем поля формы регистрации...");
        kraken.perform().fillField(Elements.Site.AuthModal.nameField(), name);
        kraken.perform().fillField(Elements.Site.AuthModal.emailField(), email);
        kraken.perform().fillField(Elements.Site.AuthModal.passwordField(), password);
        kraken.perform().fillField(Elements.Site.AuthModal.passwordConfirmationField(), passwordConfirmation);
    }

    /** Отправить форму */
    private void sendForm(){
        printMessage("> отправляем форму\n");
        kraken.perform().click(Elements.Site.AuthModal.submitButton());
    }

    /** Закрыть форму авторизации/регистрации */
    public void closeAuthModal(){
        kraken.perform().click(Elements.Site.AuthModal.closeButton());
        waitingFor(1); // Ожидание закрытия модалки авторизации
    }

    /** Переключиться на вкладку авторизации */
    private void switchToAuthorisationTab() throws Exception {
        try {
            kraken.perform().printMessage("> переключаемся на вкладку авторизации");
            kraken.perform().click(Elements.Site.AuthModal.authorisationTab());
        } catch (ElementNotInteractableException e) { // TODO попробовать перенести кетч в методы click в HelperBase
            kraken.perform().printMessage(" > что-то пошло не так, пробуем ещё раз...");
            kraken.perform().click(Elements.Site.AuthModal.authorisationTab());
        }
    }

    /** Заполнить поля формы авторизации */
    private void fillAuthorisationForm(String email, String password) {
        printMessage("> заполняем поля формы авторизации...");
        kraken.perform().fillField(Elements.Site.AuthModal.emailField(), email);
        kraken.perform().fillField(Elements.Site.AuthModal.passwordField(), password);
    }

    /** Перейти в форму восстановления пароля */
    private void proceedToPasswordRecovery(){
        printMessage("> переходим в форму восстановления пароля");
        kraken.perform().click(Elements.Site.AuthModal.forgotPasswordButton());
    }

    /** Запросить восстановление пароля */
    public void recoverPassword(String email) throws Exception {
        openAuthModal();
        switchToAuthorisationTab();
        proceedToPasswordRecovery();
        printMessage("> запрашиваем восстановление пароля для " + email);
        kraken.perform().fillField(Elements.Site.AuthModal.emailField(),email);
        sendForm();
        waitingFor(1); // Ожидание раздизабливания кнопки подтверждения восстановления пароля
    }


    // ======= Работа с заказами =======

    /** Повторить крайний заказ */
    public void repeatLastOrder(){
        printMessage("Повторяем крайний заказ...\n");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().isElementPresent(Elements.Site.OrdersPage.lastOrderActionButton(2))) {
            kraken.perform().click(Elements.Site.OrdersPage.lastOrderActionButton(2));
        } else {
            kraken.perform().click(Elements.Site.OrdersPage.lastOrderActionButton());
        }
        waitingFor(2); // Ожидание добавления в корзину товаров из предыдущего заказа
    }

    /** Отменить крайний заказ */
    public void cancelLastOrder (){
        printMessage("Отменяем крайний заказ...\n");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().isElementPresent(Elements.Site.OrdersPage.lastOrderActionButton(2))) {
            kraken.perform().click(Elements.Site.OrdersPage.lastOrderActionButton(1));
            printMessage("> OK");
        } else printMessage("> Заказ не активен");
        waitingFor(2); // Ожидание отмены заказа
    }


    // ======= Drop =======

    /** Деавторизоваться, оставшись на текущей странице */
    public void dropAuth() {
        if (kraken.detect().isUserAuthorised()) {
            String currentURL = kraken.grab().currentURL();
            quickLogout();                  // TODO ЗАТЕСТИТЬ новый способ
            //kraken.perform().logout();    // TODO УБРАТЬ старый способ
            kraken.get().url(currentURL);
        }
    }

    /** Очистить корзину изменениями адреса доставки ( временный метод, пока не запилят очистку корзины ) */
    public void dropCart() {
        String currentAddress = kraken.grab().currentShipAddress();
        String addressOne = Addresses.Moscow.defaultAddress();
        String addressTwo = Addresses.Moscow.testAddress();

        if (!kraken.detect().isCartEmpty()) {
            kraken.shopping().closeCart();
            if (currentAddress.equals(addressOne)) {
                kraken.shipAddress().change(addressTwo);
            } else {
                kraken.shipAddress().change(addressTwo);
                kraken.shipAddress().change(addressOne);
            }
        }
        kraken.shopping().closeCart();
    }


    // ======= Check =======

    /** Проверка скачивания документации на странице деталей заказа */
    public void checkOrderDocuments(){
        for(int i = 1; i <= 3; i++) {
            if(kraken.detect().orderDocument(i) != null) {
                kraken.perform().click(Elements.locator());
            }
        }
    }

    /** Проверка скачивания документации заказа */
    public void checkOrderDocuments(String orderNumber){
        kraken.get().page("user/orders/" + orderNumber);
        checkOrderDocuments();
    }


    // ======= Reach =======

    public void reachCheckout() {
        kraken.get().checkoutPage();
        if(!kraken.checkout().isOnCheckout()){
            kraken.shopping().collectItems();
            kraken.shopping().proceedToCheckout();
        }
    }

    public void reachAdmin() throws Exception{
        reachAdmin("");
    }

    public void reachAdmin(Pages page) throws Exception {
        reachAdmin(getPagePath());
    }

    public void reachAdmin(String path) throws Exception{
        kraken.get().adminPage("");
        if (kraken.detect().isOnSite()) {
            kraken.get().baseUrl();
            if (kraken.detect().isUserAuthorised()) {
                logout();
            }
            loginAs("admin");
        }
        kraken.get().adminPage(path);
    }
}
