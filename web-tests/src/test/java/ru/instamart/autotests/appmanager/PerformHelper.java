package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.autotests.application.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.multiSessionMode;
import static ru.instamart.autotests.application.Config.verbose;


public class PerformHelper extends HelperBase {

    PerformHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    // ======= Базовые действия =======

    /** Кликнуть элемент */
    public void click(ElementData element) {
        click(element.getLocator());
    }

    /** Кликнуть элемент по локатору */
    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n) {
            printMessage("Невозможно нажать на элемент <" + locator
                    + ">\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
        }
        catch (ElementNotVisibleException v) {
            printMessage("Невозможно нажать на элемент <" + locator
                    + ">\nЭлемент не отображается на " + kraken.grab().currentURL() + "\n");
        }
    }

    /** Навестисть на элемент */
    public void hoverOn(ElementData element) {
        hoverOn(element.getLocator());
    }

    /** Навестись на элемент по локатору */
    public void hoverOn(By locator) {
        try {
            new Actions(driver).moveToElement(driver.findElement(locator)).perform();
        }
        catch (ElementNotVisibleException v) {
            printMessage("Невозможно навестись на элемент <" + locator
                    + ">\nЭлемент не отображается на " + kraken.grab().currentURL() + "\n");
        }
    }

    /** Заполнить поле указанным текстом */
    public void fillField(ElementData element, String text) {
        fillField(element.getLocator(),text);
    }

    /** Заполнить поле по локатору указанным текстом */
    void fillField(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = driver.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    /** Установить значение чекбокса в соответствии */
    public void setCheckbox(ElementData element, boolean value) {
        setCheckbox(element.getLocator(), value);
    }

    public void setCheckbox(By locator, boolean value) {
        if(value) {
            if(!kraken.detect().isCheckboxSelected(locator))
                click(locator);
        } else {
            if(kraken.detect().isCheckboxSelected(locator))
                click(locator);
        }
    }

    /** Переключиться на фреймами по имени или id */
    void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    /** Переключиться на активный элемент */
    public void switchToActiveElement() {
        driver.switchTo().activeElement();
    }

    /** Переключиться на следующую вкладку */
    public void switchToNextWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
        }
    }

    /** Переключиться на дефолтный контент */
    void switchToDefaultContent() {
        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
    }

    /** Обновить страницу */
    public void refresh() {
        driver.navigate().refresh();
        kraken.await().implicitly(1); // Ожидание обновления страницы
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

    /** Зарегистрировать тестового юзера со сгенерированными реквизитами */
    public void registration() {
        registration(generate.testCredentials("user"));
    }

    /** Зарегистрировать нового юзера с реквизитами из переданного объекта UserData */
    public void registration(UserData userData) {
        registration(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword());
    }

    /** Зарегистрировать нового юзера с указанными реквизитами */
    public void registration(String name, String email, String password, String passwordConfirmation) {
        printMessage("Регистрируемся (" + email + " / " + password + ")");
        openAuthModal();
        regSequence(name,email,password,passwordConfirmation);
        // TODO переделать на fluent-ожидание
        kraken.await().implicitly(1); // Ожидание раздизебливания кнопки подтверждения регистрации
        sendForm();
    }

    /** Регистрационная последовательность с реквизитами из переданного объекта UserData */
    public void regSequence(UserData userData) {
        regSequence(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword());
    }

    /** Регистрационная последовательность с реквизитами из переданного объекта UserData */
    public void regSequence(UserData userData, boolean agreement) {
        switchToRegistrationTab();
        fillRegistrationForm(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword(), agreement);
    }

    /** Регистрационная последовательность с указанными реквизитами */
    public void regSequence(String name, String email, String password, String passwordConfirmation) {
        switchToRegistrationTab();
        fillRegistrationForm(name, email, password, passwordConfirmation, true);
    }


    // ======= Авторизация / деавторизация =======

    /** Залогиниться юзером с указанной ролью */
    public void loginAs(UserData user) throws Exception { //TODO использовать только session-юзеров
        String startURL = kraken.grab().currentURL();
        if (!startURL.equals(fullBaseUrl) && kraken.detect().isUserAuthorised()) {
            kraken.get().profilePage();
            String currentUserEmail = kraken.grab().text(Elements.Site.UserProfile.AccountPage.email());
            printMessage("Юзер: " + currentUserEmail);
            if(currentUserEmail == null || !currentUserEmail.equals(user.getEmail())) {
                quickLogout();
            }
            kraken.get().url(startURL);
        }
        authorisation(user);
        if(multiSessionMode && kraken.detect().isAuthModalOpen()) {
            printMessage(">>> Юзер " + user.getEmail() + " не найден, регистрируем\n");
            // костыль для stage-окружений
            if(kraken.environment.getServer().equals("staging")) {
                kraken.get().baseUrl();
            }
            registration(user);
            if(user.getRole().equals("admin")) {
                quickLogout();
                authorisation(Users.superadmin());
                kraken.admin().grantAdminPrivileges(user);
                quickLogout();
                authorisation(user);
            }
        }
        printMessage("Уровень прав: " + user.getRole() + "\n");
    }

    /** Залогиниться с реквизитами из переданного объекта UserData */
    public void authorisation(UserData userData) throws Exception {
        authorisation(userData.getEmail(), userData.getPassword());
    }

    /** Залогиниться с указанными реквизитами */
    public void authorisation(String email, String password) throws Exception {
        if (!kraken.detect().isUserAuthorised()) {
            printMessage("Авторизуемся (" + email + " / " + password + ")");
            openAuthModal();
            authSequence(email, password);
            sendForm();
            // TODO добавить проверку на тормоза и обернуть в нее задержку для стабильности
        } else {
            printMessage("Пропускаем авторизацию, уже авторизованы");
        }
    }

    /** Авторизационная последовательность с реквизитами из переданного объекта UserData */
    public void authSequence(UserData role) throws Exception {
        authSequence(role.getEmail(), role.getPassword());
    }

    /** Авторизационная последовательность с указанными реквизитами */
    private void authSequence(String email, String password) throws Exception {
        switchToAuthorisationTab();
        fillAuthorisationForm(email, password);
    }

    /** Деавторизоваться */
    public void logout() {
        if (!kraken.detect().isInAdmin()) {
            click(Elements.Site.Header.profileButton());
            click(Elements.Site.AccountMenu.logoutButton());
        } else {
            click(Elements.Admin.Header.logoutButton());
        }
        kraken.await().implicitly(1); // Ожидание деавторизации и подгрузки лендинга
        if(kraken.detect().isOnLanding()) {
            printMessage("Логаут\n");
        }
    }

    /** Деавторизоваться быстро по прямой ссылке */
    public void quickLogout() {
        kraken.get().page("logout");
        kraken.await().implicitly(1); // Ожидание деавторизации и подгрузки лендинга
        if(kraken.detect().isOnLanding()) {
            printMessage("Быстрый логаут\n");
        }
    }

    // ======= Работа с Gmail =======

    /** Авторизоваться в гугл почте с определённой ролью */
    public void authGmail(UserData role) {
        authGmail(Users.userGmail().getEmail(), Users.userGmail().getPassword());
    }

    /** Авторизоваться в гугл почте */
    public void authGmail(String gmail, String password) {
        if(verbose) { printMessage("> авторизовываемся в гугл почте..."); }
        kraken.get().url("https://mail.google.com/mail/u/0/h/");
        fillField(By.name("identifier"), gmail);
        click(By.id("identifierNext"));
        kraken.await().implicitly(1); // Ожидание загрузки страницы ввода пароля Gmail
        fillField(By.name("password"), password);
        click(By.id("passwordNext"));
        kraken.await().implicitly(1); // Ожидание авторизации в Gmail
    }

    /** Открыть крайнее письмо в цепочке писем от Инстамарт */
    public void openLastGmail() {
        if(verbose) { printMessage("> открываем крайнее письмо от Инстамарт"); }
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Instamart'])[1]/following::span[1]"));
        click(By.linkText("- Показать цитируемый текст -"));
    }

    /** Нажать кнопку сброса пароля в письме */
    public void clickRecoveryInMail() {
        if(verbose) { printMessage("> нажимаем кнопку сброса пароля в письме"); }
        click(By.linkText("СБРОСИТЬ ПАРОЛЬ"));
        kraken.await().implicitly(1); // Ожидание перехода из письма на сайт Инстамарт
        switchToNextWindow();
    }

    // ======= Методы модалки авторизации/регистрации =======

    /** Открыть форму авторизации/регистрации */
    public void openAuthModal(){
        if (kraken.detect().isAuthModalOpen()) {
            if(verbose) { printMessage("> модалка авторизации открыта"); }
        } else {
            if(verbose) { printMessage("> открываем модалку авторизации"); }
            if (kraken.detect().isOnLanding()) {
                click(Elements.Landing.loginButton());
            } else {
                click(Elements.Site.Header.loginButton());
            }
            kraken.await().implicitly(1); // Ожидание открытия модалки авторизации/регистрации
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Site.AuthModal.popup().getLocator())
            );
        }
    }

    /** Переключиться на вкладку регистрации */
    private void switchToRegistrationTab(){
        if(verbose) { printMessage("> переключаемся на вкладку регистрации"); }
        click(Elements.Site.AuthModal.registrationTab());
    }

    /** Заполнить поля формы регистрации */
    private void fillRegistrationForm(String name, String email, String password, String passwordConfirmation, boolean agreementConfirmation) {
        if(verbose) { printMessage("> заполняем поля формы регистрации"); }
        fillField(Elements.Site.AuthModal.nameField(), name);
        fillField(Elements.Site.AuthModal.emailField(), email);
        fillField(Elements.Site.AuthModal.passwordField(), password);
        fillField(Elements.Site.AuthModal.passwordConfirmationField(), passwordConfirmation);
        // setCheckbox(Elements.Site.AuthModal.agreementCheckbox(), agreementConfirmation);
    }

    /** Отправить форму */
    public void sendForm(){
        if(verbose) { printMessage("> отправляем форму\n"); }
        click(Elements.Site.AuthModal.submitButton());
        kraken.await().implicitly(2); // Ожидание авторизации
    }

    /** Закрыть форму авторизации/регистрации */
    public void closeAuthModal(){
        click(Elements.Site.AuthModal.closeButton());
        kraken.await().implicitly(1); // Ожидание закрытия модалки авторизации
    }

    /** Переключиться на вкладку авторизации */
    private void switchToAuthorisationTab() throws Exception {
        try {
            if(verbose) { printMessage("> переключаемся на вкладку авторизации"); }
            click(Elements.Site.AuthModal.authorisationTab());
        } catch (ElementNotInteractableException e) {
            printMessage(" > что-то пошло не так, пробуем ещё раз...");
            if(verbose) { click(Elements.Site.AuthModal.authorisationTab()); }
        }
    }

    /** Заполнить поля формы авторизации */
    private void fillAuthorisationForm(String email, String password) {
        if(verbose) { printMessage("> заполняем поля формы авторизации..."); }
        fillField(Elements.Site.AuthModal.emailField(), email);
        fillField(Elements.Site.AuthModal.passwordField(), password);
    }

    /** Перейти в форму восстановления пароля */
    private void proceedToPasswordRecovery(){
        if(verbose) { printMessage("> переходим в форму восстановления пароля"); }
        click(Elements.Site.AuthModal.forgotPasswordButton());
    }

    /** Запросить восстановление пароля */
    public void recoverPassword(String email) throws Exception {
        openAuthModal();
        switchToAuthorisationTab();
        proceedToPasswordRecovery();
        if(verbose) { printMessage("> запрашиваем восстановление пароля для " + email); }
        fillField(Elements.Site.AuthModal.emailField(),email);
        sendForm();
        kraken.await().implicitly(1); // Ожидание раздизабливания кнопки подтверждения восстановления пароля
    }

    /** Запросить восстановление пароля для указанной роли*/
    public void recoverPasswordAs(UserData role) throws Exception {
        recoverPassword(role.getEmail());
    }

    /** Придумать новый пароль для восстановления пароля */
    public void submitRecovery(String password, String passwordConfirmation) {
        printMessage("> задаем новый пароль...\n");
        fillField(By.name("password"), password);
        fillField(By.name("passwordConfirmation"), passwordConfirmation);
        click(By.className("auth-modal__button"));
    }


    // ======= Работа с заказами =======

    /** Оформить тестовый заказ */
    public void order() {
        if (!kraken.detect().isShippingAddressSet()) {
            kraken.shipAddress().set(Addresses.Moscow.defaultAddress());
        }
            kraken.shopping().collectItems();
            kraken.shopping().proceedToCheckout();
            kraken.checkout().complete();
    }

    /** Повторить крайний заказ */
    public void repeatLastOrder() {
        printMessage("Повторяем крайний заказ\n");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().element(Elements.Site.UserProfile.OrdersPage.placeholder())) {
            printMessage("У пользователя нет заказов для повтора, делаем новый заказ\n");
            kraken.get().page("metro");
            order();
            cancelLastOrder();
        }
        if(kraken.detect().isLastOrderActive()) {
            if(verbose) {printMessage("Для повтора жмем 2 кнопку\n");}
            click(Elements.Site.UserProfile.OrdersPage.lastOrderActionButton(2));
        } else {
            if(verbose) {printMessage("Для повтора жмем 1 кнопку\n");}
            click(Elements.Site.UserProfile.OrdersPage.lastOrderActionButton());
        }
        // TODO заменить на умное ожидание
        kraken.await().implicitly(2); // Ожидание добавления в корзину товаров из предыдущего заказа
        if(kraken.detect().isInProfile()){
            printMessage("❕Тормозит повтор заказа❕");
            kraken.await().implicitly(2); // Доп. ожидание добавления в корзину товаров из предыдущего заказа при тормозах
        }
    }

    /** Отменить крайний заказ */
    public void cancelLastOrder() {
        printMessage("Отменяем крайний заказ");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().isLastOrderActive()) {
            click(Elements.Site.UserProfile.OrdersPage.lastOrderActionButton(1));
            printMessage("✓ OK\n");
        } else printMessage("> Заказ не активен\n");
        kraken.await().implicitly(2); // Ожидание отмены заказа
    }
}
