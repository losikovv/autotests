package instamart.ui.modules;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import instamart.core.common.AppManager;
import instamart.ui.objectsmap.Elements;
import instamart.core.testdata.Users;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import instamart.api.objects.v2.Address;
import instamart.core.testdata.ui.generate;

import static instamart.core.settings.Config.CoreSettings.multiSessionMode;

public class User extends Base {

    public User(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public static class Do {

        @Step("Авторизация юзером")
        public static void loginAs(UserData user) { //TODO использовать только session-юзеров
            String startURL = kraken.grab().currentURL();
            if (!startURL.equals(kraken.environment.getBasicUrlWithHttpAuth()) && kraken.detect().isUserAuthorised()) {
                kraken.get().userProfilePage();
                String currentUserEmail = kraken.grab().text(Elements.UserProfile.AccountPage.email());
                verboseMessage("> юзер: " + currentUserEmail);
                if (currentUserEmail == null || !currentUserEmail.equals(user.getLogin())) {
                    User.Logout.quickly();
                }
                kraken.get().url(startURL);
            }
            Auth.withEmail(user);
            if (multiSessionMode && kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль"))) {
                verboseMessage(">>> Юзер " + user.getLogin() + " не найден, регистрируем\n");
                // костыль для stage-окружений
                if (kraken.detect().server("staging")) {
                    kraken.get().baseUrl();
                }
                Auth.withEmail(user);
                if (user.getRole().equals("admin")) {
                    User.Logout.quickly();
                    Auth.withEmail(Users.superadmin());
                    Administration.Users.grantAdminPrivileges(user);
                    User.Logout.quickly();
                    Auth.withEmail(user);
                }
            }
            verboseMessage("> уровень прав: " + user.getRole() + "\n");
        }
        @Step("Авторизуемся на лендинге Сбермаркет")
        private static void loginOnSberLanding(String email, String password) {
            verboseMessage("> авторизуемся на лендинге Сбермаркет (" + email + " / " + password + ")");
            kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.fillAuthorisationForm(email, password);
            Shop.AuthModal.submit();
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()), "Не проходит авторизация на лендинге Сбермаркет\n");
        }
        @Step("Авторизуемся на сайте")
        private static void loginOnSite(String email, String password) {
            verboseMessage("> авторизуемся на сайте (" + email + " / " + password + ")");
            Shop.AuthModal.open();
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.fillAuthorisationForm(email, password);
            Shop.AuthModal.submit();
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()), "Не проходит авторизация на сайте\n");
        }
        @Step("Авторизуемся в админке")
        private static void loginOnAdministration(String email, String password) {
            verboseMessage("> авторизуемся в админке (" + email + " / " + password + ")");
            kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), email);
            kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), password);
            kraken.perform().click(Elements.Administration.LoginPage.submitButton());
            kraken.await().fluently(
                    ExpectedConditions.presenceOfElementLocated(
                            Elements.Administration.Header.userEmail().getLocator()), "Не проходит авторизация в админке\n");
        }
        @Step("Деавторизуемся на сайте")
        private static void logoutOnSite() {
            verboseMessage("> деавторизуемся на сайте");
            for (int i=0;i<60;i++){
                kraken.perform().click(Elements.Header.profileButton());
                if(driver.findElement(Elements.AccountMenu.logoutButton().getLocator()).isDisplayed()){
                    break;
                }else{
                    kraken.await().simply(0.3);
                }
            }
            kraken.perform().click(Elements.AccountMenu.logoutButton());
        }
        @Step("Деавторизуемся в админке")
        private static void logoutOnAdministration() {
            verboseMessage("> деавторизуемся в админке");
            kraken.perform().click(Elements.Administration.Header.logoutButton());
        }


        // ======= Регистрация =======

        /**
         * Зарегистрировать тестового юзера со сгенерированными реквизитами
         */
        @Step("Регистрируем тестового юзера со сгенерированными реквизитами")
        public static UserData registration() {
            UserData data = generate.testCredentials("user");
            registration(data);
            return data;
        }

        /**
         * Зарегистрировать нового юзера с реквизитами из переданного объекта UserData
         */
        @Step("Регестрируем нового юзера с реквизитами из переданного объекта UserData: {0}")
        public static void registration(UserData userData) {
            registration(userData.getName(), userData.getLogin(), userData.getPassword(),
                    userData.getPassword());
        }

        /**
         * Зарегистрировать нового юзера с указанными реквизитами
         */
        @Step("Регистрируем нового юзера с указанными реквизитами")
        public static void registration(String name, String email, String password,
                                        String passwordConfirmation) {
            verboseMessage("> регистрируемся (" + email + " / " + password + ")");
            Shop.AuthModal.open();
            regSequence(name, email, password, passwordConfirmation);
            // TODO переделать на fluent-ожидание
            kraken.await().implicitly(1); // Ожидание раздизебливания кнопки подтверждения регистрации
            Shop.AuthModal.submitRegistration();
        }

        /**
         * Зарегистрировать нового юзера с указанными реквизитами с проверкой на тип модалки по телефону или по почте
         */
        @Step("Регистрируем нового юзера с указанными реквизитами с проверкой на тип модалки по телефону или по почте")
        public static String registration(String name, String email,
                                        String password, String passwordConfirmation,
                                        String phone,String sms) {
            verboseMessage("> регистрируемся (" + email + " / " + password + ")");
            Shop.AuthModal.open();
            String modalType = Shop.AuthModal.checkAutorisationModalDialog();
            if(modalType.equals("модалка с телефоном")){
                regSequenceMobile(phone, sms);
                return modalType;
            }else {
                regSequence(name, email, password, passwordConfirmation);
                // TODO переделать на fluent-ожидание
                kraken.await().implicitly(1); // Ожидание раздизебливания кнопки подтверждения регистрации
                Shop.AuthModal.submitRegistration();
                return modalType;
            }
        }

        /**
         * Регистрационная последовательность с реквизитами из переданного объекта UserData
         */
        public static void regSequence(UserData userData) {
            regSequence(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
        }

        /**
         * Регистрационная последовательность с реквизитами из переданного объекта UserData
         */
        public static void regSequence(UserData userData, boolean agreement) {
            Shop.AuthModal.switchToRegistrationTab();
            Shop.AuthModal.fillRegistrationForm(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword(), agreement);
        }

        /**
         * Регистрационная последовательность с указанными реквизитами
         */
        public static void regSequence(String name, String email, String password, String passwordConfirmation) {
            Shop.AuthModal.switchToRegistrationTab();
            Shop.AuthModal.fillRegistrationForm(name, email, password, passwordConfirmation, true);
        }

        public static void regSequenceMobile(String phone, String sms){
            //todo доделать регистрацию через мобилку
            Shop.AuthModal.fillRegistrationFormByPhone(phone, sms);
        }


        public static class Gmail{

            public static void auth() {
                auth(Users.gmail().getLogin(), Users.gmail().getPassword());
            }

            @Step("Авторизуемся через Gmail")
            public static void auth(String login, String password) {
                verboseMessage("> переходим в веб-интерфейс Gmail...");
                kraken.get().url("https://mail.google.com/mail/u/0/h/");
                if(kraken.detect().isElementPresent(Elements.Social.Gmail.AuthForm.loginField())) {
                    verboseMessage("> авторизуемся в Gmail...");
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.loginField(), login);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.loginNextButton());
                    kraken.await().simply(1); // Ожидание загрузки страницы ввода пароля Gmail
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.passwordField(), password);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.passwordNextButton());
                    kraken.await().simply(1); // Ожидание авторизации в Gmail
                }
            }

            @Step("открываем последнее полученное письмо от СберМаркет")
            public static void openLastMail() {
                verboseMessage("> открываем последнее полученное письмо от СберМаркет");
                kraken.perform().click(Elements.EmailConfirmation.lastEmail());
                kraken.perform().click(Elements.EmailConfirmation.linkText());
            }

            @Step("Нажимаем кнопку сброса пароля в письме")
            public static void proceedToRecovery() {
                verboseMessage("> нажимаем кнопку сброса пароля в письме");
                kraken.perform().click(Elements.EmailConfirmation.passwordRecovery());
                kraken.await().implicitly(1); // Ожидание перехода из письма на сайт Инстамарт
                kraken.perform().switchToNextWindow();
            }
        }
    }

    public static class Auth {

        public static void withEmail(UserData user) {
            withEmail(user.getLogin(), user.getPassword());
        }

        @Step("Авторизация через email")
        public static void withEmail(String email, String password) {
            if (kraken.detect().isOnAdminLoginPage()) {
                User.Do.loginOnAdministration(email, password);
            } else {
                if (!kraken.detect().isUserAuthorised()) {
                    User.Do.loginOnSite(email, password);
                } else {
                    verboseMessage("> пропускаем авторизацию, уже авторизованы");
                }
            }
        }

        @Step("Переходим на base url для авторизации через Vkontakte")
        public static void withVkontakte(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("> переходим на base url для авторизации через Vkontakte");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitVkontakteButton();
            kraken.await().simply(2); // Ожидание открытия фрейма авторизации Vkontakte
            kraken.perform().switchToNextWindow();

            kraken.perform().fillField(Elements.Social.Vkontakte.loginField(),user.getLogin());
            kraken.perform().fillField(Elements.Social.Vkontakte.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Vkontakte.submitButton());
            kraken.await().simply(1); // Ожидание авторизации через Vkontakte

            kraken.await().fluentlyWithWindowsHandler(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()));
        }

        @Step("Переходим на base url для авторизации через Facebook")
        public static void withFacebook(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("> переходим на base url для авторизации через Facebook");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitFacebookButton();
            kraken.await().simply(2); // Ожидание открытия фрейма авторизации Facebook
            kraken.perform().switchToNextWindow();

            kraken.perform().fillField(Elements.Social.Facebook.loginField(),user.getLogin());
            kraken.perform().fillField(Elements.Social.Facebook.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Facebook.submitButton());
            kraken.await().simply(1); // Ожидание авторизации через Facebook

            //TOdo добавить проверку на то что вернулись в основное окно
            if(!kraken.detect().isOnSite()){
                kraken.perform().switchToNextWindow();
                kraken.perform().switchToDefaultContent();
            }
            kraken.await().fluentlyWithWindowsHandler(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()));
        }

        @Step("Переходим на base url для авторизации через Mail.ru")
        public static void withMailRu(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("> переходим на base url для авторизации через Mail.ru");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitMailRuButton();
            kraken.await().simply(5); // Ожидание открытия фрейма авторизации Mail.ru
            kraken.perform().switchToNextWindow();

            kraken.perform().fillField(Elements.Social.MailRu.loginField(),user.getLogin());
            kraken.perform().click(Elements.Social.MailRu.nextButton());
            kraken.await().simply(2);
            kraken.perform().click(Elements.Social.MailRu.passwordFieldUp());
            kraken.perform().fillField(Elements.Social.MailRu.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.MailRu.submitButton());

            kraken.await().fluentlyWithWindowsHandler(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()));
        }

        @Step("Переходим на base url для авторизации через Sber ID")
        public static void withSberID(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("> переходим на base url для авторизации через Sber ID");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitSberIdButton();
            //TODO тест сломан, отладить на новом стейдже
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Social.Sber.sberButtonsSection().getLocator()));
            WebElement parent = driver.findElement(Elements.Social.Sber.sberButtonsSection().getLocator());
            kraken.perform().findChildElementByTagAndText(parent,By.tagName("button"),"Логин").click();

            kraken.perform().fillField(Elements.Social.Sber.loginField(),user.getLogin());
            kraken.perform().fillField(Elements.Social.Sber.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Sber.submitButton());
            //kraken.await().simply(3); // Ожидание авторизации через Sber ID

            //kraken.perform().switchToNextWindow();
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()));
        }
    }

    public static class Logout {

        /** Ручная деавторизация через пользовательское меню */
        @Step("Деавторизуемся через пользовательское меню")
        public static void manually() {
            catchAndCloseAd(Elements.Modals.AuthModal.expressDelivery(),1);
            if(kraken.detect().isUserAuthorised()) {
                verboseMessage("> логаут...");
                if (kraken.detect().isInAdmin()) {
                    User.Do.logoutOnAdministration();
                } else {
                    User.Do.logoutOnSite();
                }
                kraken.await().implicitly(1); // Ожидание деавторизации и подгрузки лендинга
                if (!kraken.detect().isUserAuthorised()) {
                    verboseMessage("✓ Готово\n");
                }
            } else {
                verboseMessage("> пропускаем деавторизацию, уже разлогинены");
            }
        }

        /** Быстрая деавторизация прямым переходом на /logout */
        @Step("Деавторизация прямым переходом на /logout")
        public static void quick() {
            verboseMessage("> быстрый логаут...");
            kraken.get().page("logout");
            kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                verboseMessage("✓ Готово\n");
            }
        }

        /** Быстрая деавторизация удалением кук */
        @Step("Делаем быструю деавторизацию пользователя с удалением файлов куки")
        public static void quickly() {
            verboseMessage("> удаляем куки...");
            driver.manage().deleteAllCookies();
            kraken.get().baseUrl();
            //kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                verboseMessage("✓ Готово\n");
            }
        }
    }

    public static class ShippingAddress {

        public static void set(Address address,boolean submit) {
            set(address.getCity() + " " + address.getStreet() + " " + address.getBuilding(), submit);
        }

        /** Установить адрес доставки */
        @Step("Устанавливаем адрес доставки: {0}, сохраняем? {1}")
        public static void set(String address, boolean submit) {
            Shop.ShippingAddressModal.open();
            kraken.await().fluently(
                    ExpectedConditions.elementToBeClickable(
                            Elements.Modals.AddressModal.modalMapWithText().getLocator()),
                    "Не подтянулись адресные подсказки\n"
            );
            String currentAddress = kraken.grab().value(Elements.Modals.AddressModal.addressField());
            if(currentAddress.equals("")){
                kraken.await().implicitly(3);//текущая локация подставляется автоматически,
                // нужно подождать, пока элемент прогрузится
                currentAddress = kraken.grab().value(Elements.Modals.AddressModal.addressField());
                if(currentAddress.equals(""))verboseMessage("> устанавливаем адрес доставки: " + address + "\n");
                else Shop.ShippingAddressModal.clearAddressField();
            } else {
                verboseMessage("> изменяем адрес доставки:\n" + currentAddress + " >>> " + address + "\n");
                Shop.ShippingAddressModal.clearAddressField();
            }
            //todo нужно допилить проверку"
            Shop.ShippingAddressModal.fill(address);
            if(submit){
                kraken.await().simply(2);
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Modals.AddressModal.submitButton().getLocator()),
                        "Кнопка сохранить не активна\n"
                );
                Shop.ShippingAddressModal.submit();
            }
        }

        /** Свапнуть тестовый и дефолтные адреса */
        @Step("Свапаем тестовый и дефолтные адреса")
        public static void swap() {
            if (kraken.grab().currentShipAddress().equals(Addresses.Moscow.defaultAddress())) {
                set(Addresses.Moscow.testAddress(),true);
            } else {
                set(Addresses.Moscow.testAddress(),true);
                set(Addresses.Moscow.defaultAddress(),true);
            }
        }
    }

    public static class PasswordRecovery {

        public static void request(UserData user) {
            request(user.getLogin());
        }

        @Step("Запрашиваем восстановление пароля для: {0}")
        public static void request(String email) {
            Shop.AuthModal.open();
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.proceedToPasswordRecovery();
            verboseMessage("> запрашиваем восстановление пароля для " + email);
            Shop.RecoveryModal.fillRequestForm(email);
            Shop.RecoveryModal.submitRequest();
        }

        @Step("Восстановливаем пароль: {0} {1}")
        public static void complete(UserData user, String recoveredPassword) {
            User.Do.Gmail.auth();
            User.Do.Gmail.openLastMail();
            User.Do.Gmail.proceedToRecovery();
            verboseMessage("> восстановливаем пароль '" + recoveredPassword + "' для " + user.getLogin());
            Shop.RecoveryModal.fillRecoveryForm(recoveredPassword, recoveredPassword);
            Shop.RecoveryModal.submitRecovery();
        }
    }
}
