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


public class PerformHelper extends HelperBase {

    PerformHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    // ======= Базовые действия =======

    // TODO Обернуть методы click один в другой
    /** Кликнуть элемент */
    public void click(ElementData element) {
        try {
            debugMessage("Клик по: " + element.getDescription());
            driver.findElement(element.getLocator()).click();
        }
        catch (NoSuchElementException n) {
            message("Не нажимается " + element.getDescription()
                    + "\nЭлемент не найден по " + element.getLocator().toString().substring(3) + "\nна " + kraken.grab().currentURL() + "\n");
        }
        catch (ElementNotVisibleException v) {
            message("Не нажимается " + element.getDescription()
                    + "\nЭлемент по " + element.getLocator().toString().substring(3) + " невидим\nна " + kraken.grab().currentURL() + "\n");
        }
    }

    // TODO убрать все использования в тестах и хелперах, оставить метод только для удобства дебага
    /** Кликнуть элемент по локатору */
    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n) {
            message("Невозможно нажать на элемент <" + locator
                    + ">\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
        }
        catch (ElementNotVisibleException v) {
            message("Невозможно нажать на элемент <" + locator
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
            message("Невозможно навестись на элемент <" + locator
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
        message("Регистрируемся (" + email + " / " + password + ")");
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
            String currentUserEmail = kraken.grab().text(Elements.UserProfile.AccountPage.email());
            message("Юзер: " + currentUserEmail);
            if(currentUserEmail == null || !currentUserEmail.equals(user.getEmail())) {
                quickLogout();
            }
            kraken.get().url(startURL);
        }
        authorisation(user);
        if(multiSessionMode && kraken.detect().isElementPresent(
                Elements.Modals.AuthModal.errorMessage("Неверный email или пароль"))) {
                    message(">>> Юзер " + user.getEmail() + " не найден, регистрируем\n");
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
        message("Уровень прав: " + user.getRole() + "\n");
    }

    /** Залогиниться с реквизитами из переданного объекта UserData */
    public void authorisation(UserData userData) {
        authorisation(userData.getEmail(), userData.getPassword());
    }

    /** Залогиниться с указанными реквизитами */
    public void authorisation(String email, String password) {
        if (!kraken.detect().isUserAuthorised()) {
            message("Авторизуемся (" + email + " / " + password + ")");
            openAuthModal();
            authSequence(email, password);
            sendForm();
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()), "Не проходит авторизация\n");
        } else {
            message("Пропускаем авторизацию, уже авторизованы");
        }
    }

    /** Авторизационная последовательность с реквизитами из переданного объекта UserData */
    public void authSequence(UserData role) {
        authSequence(role.getEmail(), role.getPassword());
    }

    /** Авторизационная последовательность с указанными реквизитами */
    public void authSequence(String email, String password) {
        switchToAuthorisationTab();
        fillAuthorisationForm(email, password);
    }

    /** Деавторизоваться */
    public void logout() {
        if (kraken.detect().isInAdmin()) {
            click(Elements.Admin.Header.logoutButton());
        } else {
            click(Elements.Header.profileButton());
            click(Elements.AccountMenu.logoutButton());
        }
        kraken.await().implicitly(1); // Ожидание деавторизации и подгрузки лендинга
        if(kraken.detect().isOnLanding()) {
            verboseMessage("Логаут\n");
        }
    }

    /** Деавторизоваться быстро по прямой ссылке */
    public void quickLogout() {
        kraken.get().page("logout");
        kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
        if(kraken.detect().isOnLanding()) {
            message("Быстрый логаут\n");
        }
    }

    // ======= Работа с Gmail =======

    /** Авторизоваться в гугл почте с определённой ролью */
    public void authGmail(UserData role) {
        authGmail(Users.userGmail().getEmail(), Users.userGmail().getPassword());
    }

    /** Авторизоваться в гугл почте */
    public void authGmail(String gmail, String password) {
        verboseMessage("> авторизовываемся в гугл почте...");
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
        verboseMessage("> открываем крайнее письмо от Инстамарт");
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Instamart'])[1]/following::span[1]"));
        click(By.linkText("- Показать цитируемый текст -"));
    }

    /** Нажать кнопку сброса пароля в письме */
    public void clickRecoveryInMail() {
        verboseMessage("> нажимаем кнопку сброса пароля в письме");
        click(By.linkText("СБРОСИТЬ ПАРОЛЬ"));
        kraken.await().implicitly(1); // Ожидание перехода из письма на сайт Инстамарт
        switchToNextWindow();
    }

    // ======= Методы модалки авторизации/регистрации =======

    /** Открыть форму авторизации/регистрации */
    public void openAuthModal(){
        if (!kraken.detect().isAuthModalOpen()) {
            verboseMessage("> открываем модалку авторизации");
            if (kraken.detect().isOnLanding()) {
                click(Elements.Landing.loginButton());
            } else {
                click(Elements.Header.loginButton());
            }
            kraken.await().implicitly(1); // Ожидание открытия модалки авторизации/регистрации
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator())
            );
        }
    }

    /** Переключиться на вкладку регистрации */
    private void switchToRegistrationTab(){
        verboseMessage("> переключаемся на вкладку регистрации");
        click(Elements.Modals.AuthModal.registrationTab());
    }

    /** Заполнить поля формы регистрации */
    private void fillRegistrationForm(String name, String email, String password, String passwordConfirmation, boolean agreementConfirmation) {
        verboseMessage("> заполняем поля формы регистрации");
        fillField(Elements.Modals.AuthModal.nameField(), name);
        fillField(Elements.Modals.AuthModal.emailField(), email);
        fillField(Elements.Modals.AuthModal.passwordField(), password);
        fillField(Elements.Modals.AuthModal.passwordConfirmationField(), passwordConfirmation);
        if (!agreementConfirmation) {
            click(Elements.Modals.AuthModal.agreementCheckbox());
        }
    }

    /** Отправить форму */
    public void sendForm(){
        verboseMessage("> отправляем форму\n");
        click(Elements.Modals.AuthModal.submitButton());
        kraken.await().implicitly(1); // Ожидание авторизации
    }

    /** Закрыть форму авторизации/регистрации */
    public void closeAuthModal(){
        click(Elements.Modals.AuthModal.closeButton());
        kraken.await().implicitly(1); // Ожидание закрытия модалки авторизации
    }

    /** Переключиться на вкладку авторизации */
    private void switchToAuthorisationTab() {
        try {
            verboseMessage("> переключаемся на вкладку авторизации");
            click(Elements.Modals.AuthModal.authorisationTab());
        } catch (ElementNotInteractableException e) {
            debugMessage(" > что-то пошло не так, пробуем ещё раз...");
            click(Elements.Modals.AuthModal.authorisationTab());
        }
    }

    /** Заполнить поля формы авторизации */
    private void fillAuthorisationForm(String email, String password) {
        verboseMessage("> заполняем поля формы авторизации...");
        fillField(Elements.Modals.AuthModal.emailField(), email);
        fillField(Elements.Modals.AuthModal.passwordField(), password);
    }

    /** Перейти в форму восстановления пароля */
    private void proceedToPasswordRecovery(){
        verboseMessage("> переходим в форму восстановления пароля");
        click(Elements.Modals.AuthModal.forgotPasswordButton());
    }

    /** Запросить восстановление пароля */
    public void recoverPassword(String email) {
        openAuthModal();
        switchToAuthorisationTab();
        proceedToPasswordRecovery();
        verboseMessage("> запрашиваем восстановление пароля для " + email);
        fillField(Elements.Modals.AuthModal.emailField(),email);
        sendForm();
        kraken.await().implicitly(1); // Ожидание раздизабливания кнопки подтверждения восстановления пароля
    }

    /** Запросить восстановление пароля для указанной роли*/
    public void recoverPasswordAs(UserData role) {
        recoverPassword(role.getEmail());
    }

    /** Придумать новый пароль для восстановления пароля */
    public void submitRecovery(String password, String passwordConfirmation) {
        message("> задаем новый пароль...\n");
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
        ShopHelper.Cart.proceedToCheckout();
        kraken.checkout().complete();
    }

    /** Повторить крайний заказ */
    public void repeatLastOrder() {
        message("Повторяем крайний заказ\n");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().element(Elements.UserProfile.OrdersPage.placeholder())) {
            message("У пользователя нет заказов для повтора, делаем новый заказ\n");
            kraken.get().page("metro");
            order();
            cancelLastOrder();
        }
        if(kraken.detect().isLastOrderActive()) {
            verboseMessage("Для повтора жмем 2 кнопку\n");
            click(Elements.UserProfile.OrdersPage.lastOrderActionButton(2));
        } else {
            verboseMessage("Для повтора жмем 1 кнопку\n");
            click(Elements.UserProfile.OrdersPage.lastOrderActionButton());
        }
        // TODO заменить на умное ожидание
        kraken.await().implicitly(2); // Ожидание добавления в корзину товаров из предыдущего заказа
        if(kraken.detect().isInProfile()){
            message("❕Тормозит повтор заказа❕");
            kraken.await().implicitly(2); // Доп. ожидание добавления в корзину товаров из предыдущего заказа при тормозах
        }
    }

    /** Отменить крайний заказ */
    public void cancelLastOrder() {
        message("Отменяем крайний заказ");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().isLastOrderActive()) {
            click(Elements.UserProfile.OrdersPage.lastOrderActionButton(1));
            message("✓ OK\n");
        } else message("> Заказ не активен\n");
        kraken.await().implicitly(2); // Ожидание отмены заказа
    }
}
