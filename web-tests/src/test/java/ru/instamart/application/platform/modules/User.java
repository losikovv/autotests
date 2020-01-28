package ru.instamart.application.platform.modules;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.application.Elements;
import ru.instamart.application.Users;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.models.UserData;
import ru.instamart.application.rest.objects.Address;
import ru.instamart.testdata.generate;

import static ru.instamart.application.Config.CoreSettings.multiSessionMode;

public class User extends Base {

    public User(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public static class Do {

        public static void loginAs(UserData user) { //TODO использовать только session-юзеров
            String startURL = kraken.grab().currentURL();
            if (!startURL.equals(kraken.environment.getBasicUrlWithHttpAuth()) && kraken.detect().isUserAuthorised()) {
                kraken.get().userProfilePage();
                String currentUserEmail = kraken.grab().text(Elements.UserProfile.AccountPage.email());
                message("Юзер: " + currentUserEmail);
                if (currentUserEmail == null || !currentUserEmail.equals(user.getLogin())) {
                    User.Logout.quickly();
                }
                kraken.get().url(startURL);
            }
            Auth.withEmail(user);
            if (multiSessionMode && kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль"))) {
                message(">>> Юзер " + user.getLogin() + " не найден, регистрируем\n");
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
            message("Уровень прав: " + user.getRole() + "\n");
        }

        private static void loginOnSberLanding(String email, String password) {
            verboseMessage("Авторизуемся на лендинге Сбермаркет (" + email + " / " + password + ")");
            kraken.perform().click(Elements.Landings.SbermarketLanding.Header.loginButton());
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.fillAuthorisationForm(email, password);
            Shop.AuthModal.submit();
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()), "Не проходит авторизация на лендинге Сбермаркет\n");
        }

        private static void loginOnSite(String email, String password) {
            verboseMessage("Авторизуемся на сайте (" + email + " / " + password + ")");
            Shop.AuthModal.open();
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.fillAuthorisationForm(email, password);
            Shop.AuthModal.submit();
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()), "Не проходит авторизация на сайте\n");
        }

        private static void loginOnAdministration(String email, String password) {
            verboseMessage("Авторизуемся в админке (" + email + " / " + password + ")");
            kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), email);
            kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), password);
            kraken.perform().click(Elements.Administration.LoginPage.submitButton());
            kraken.await().fluently(
                    ExpectedConditions.presenceOfElementLocated(
                            Elements.Administration.Header.userEmail().getLocator()), "Не проходит авторизация в админке\n");
        }

        private static void logoutOnSite() {
            verboseMessage("> Деавторизуемся на сайте");
            kraken.perform().click(Elements.Header.profileButton());
            kraken.perform().click(Elements.AccountMenu.logoutButton());
        }

        private static void logoutOnAdministration() {
            verboseMessage("> Деавторизуемся в админке");
            kraken.perform().click(Elements.Administration.Header.logoutButton());
        }


        // ======= Регистрация =======

        /**
         * Зарегистрировать тестового юзера со сгенерированными реквизитами
         */
        public static UserData registration() {
            UserData data = generate.testCredentials("user");
            registration(data);
            return data;
        }

        /**
         * Зарегистрировать нового юзера с реквизитами из переданного объекта UserData
         */
        public static void registration(UserData userData) {
            registration(userData.getName(), userData.getLogin(), userData.getPassword(), userData.getPassword());
        }

        /**
         * Зарегистрировать нового юзера с указанными реквизитами
         */
        public static void registration(String name, String email, String password, String passwordConfirmation) {
            //todo попробовать обернуть в проверку авторизованности и делать логаут при необходимости
            message("Регистрируемся (" + email + " / " + password + ")");
            Shop.AuthModal.open();
            regSequence(name, email, password, passwordConfirmation);
            // TODO переделать на fluent-ожидание
            kraken.await().implicitly(1); // Ожидание раздизебливания кнопки подтверждения регистрации
            Shop.AuthModal.submit();
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


        public static class Gmail{

            public static void auth() {
                auth(Users.gmail().getLogin(), Users.gmail().getPassword());
            }

            public static void auth(String login, String password) {
                verboseMessage("> переходим в веб-интерфейс Gmail...");
                kraken.get().url("https://mail.google.com/mail/u/0/h/");
                if(kraken.detect().isElementPresent(Elements.Social.Gmail.AuthForm.loginField())) {
                    verboseMessage("> авторизуепмся в Gmail...");
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.loginField(), login);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.loginNextButton());
                    kraken.await().simply(1); // Ожидание загрузки страницы ввода пароля Gmail
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.passwordField(), password);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.passwordNextButton());
                    kraken.await().simply(1); // Ожидание авторизации в Gmail
                }
            }

            public static void openLastMail() {
                verboseMessage("> открываем крайнее письмо от Инстамарт");
                kraken.perform().click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Instamart'])[1]/following::span[1]"));
                kraken.perform().click(By.linkText("- Показать цитируемый текст -"));
            }

            public static void proceedToRecovery() {
                verboseMessage("> нажимаем кнопку сброса пароля в письме");
                kraken.perform().click(By.xpath("//a[contains(text(),'Продолжить')]"));
                kraken.await().implicitly(1); // Ожидание перехода из письма на сайт Инстамарт
                kraken.perform().switchToNextWindow();
            }
        }
    }

    public static class Auth {

        public static void withEmail(UserData user) {
            withEmail(user.getLogin(), user.getPassword());
        }

        public static void withEmail(String email, String password) {
            if (kraken.detect().isOnAdminLoginPage()) {
                User.Do.loginOnAdministration(email, password);
            } else {
                if (!kraken.detect().isUserAuthorised()) {
                    User.Do.loginOnSite(email, password);
                } else {
                    verboseMessage("Пропускаем авторизацию, уже авторизованы");
                }
            }
        }

        public static void withVkontakte(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("Переходим на base url для авторизации через Vkontakte");
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

            kraken.perform().switchToNextWindow();
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()));
        }

        public static void withFacebook(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("Переходим на base url для авторизации через Facebook");
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

            kraken.perform().switchToNextWindow();
            //TOdo добавить проверку на то что вернулись в основное окно
            if(!kraken.detect().isOnSite()){
                kraken.perform().switchToNextWindow();
                kraken.perform().switchToDefaultContent();
            }
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()));
        }

        public static void withMailRu(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("Переходим на base url для авторизации через Mail.ru");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitMailRuButton();
            kraken.await().simply(2); // Ожидание открытия фрейма авторизации Mail.ru
            kraken.perform().switchToNextWindow();

            kraken.perform().fillField(Elements.Social.MailRu.loginField(),user.getLogin());
            kraken.perform().fillField(Elements.Social.MailRu.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.MailRu.submitButton());

            kraken.perform().click(Elements.Social.MailRu.loginButton(user.getLogin()));
            kraken.await().simply(1); // Ожидание авторизации через Mail.ru

            kraken.perform().switchToNextWindow();
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()));
        }

        public static void withSberID(UserData user) {
            if (kraken.detect().isInAdmin()) {
                verboseMessage("Переходим на base url для авторизации через Sber ID");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitSberIdButton();
            kraken.await().simply(2); // Ожидание открытия фрейма авторизации Sber ID
            kraken.perform().switchToNextWindow();

            if (kraken.detect().isElementPresent(Elements.Social.Sber.loginField())) {
                kraken.perform().click(Elements.Social.Sber.loginButton());
            }

            kraken.perform().fillField(Elements.Social.Sber.loginField(),user.getLogin());
            kraken.perform().fillField(Elements.Social.Sber.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Sber.submitButton());
            kraken.await().simply(3); // Ожидание авторизации через Sber ID

            kraken.perform().switchToNextWindow();
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(Elements.Modals.AuthModal.popup().getLocator()));
        }
    }

    public static class Logout {

        /** Ручная деавторизация через пользовательское меню */
        public static void manually() {
            if(kraken.detect().isUserAuthorised()) {
                verboseMessage("Логаут...");
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
                verboseMessage("Пропускаем деавторизацию, уже разлогинены");
            }
        }

        /** Быстрая деавторизация прямым переходом на /logout */
        public static void quick() {
            verboseMessage("Быстрый логаут...");
            kraken.get().page("logout");
            kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                verboseMessage("✓ Готово\n");
            }
        }

        /** Быстрая деавторизация удалением кук */
        public static void quickly() {
            verboseMessage("Удаляем куки...");
            driver.manage().deleteAllCookies();
            kraken.get().baseUrl();
            //kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                verboseMessage("✓ Готово\n");
            }
        }
    }

    public static class ShippingAddress {

        public static void set(Address address) {
            set(address.getCity() + " " + address.getStreet() + " " + address.getBuilding());
        }

        /** Установить адрес доставки */
        public static void set(String address) {
            Shop.ShippingAddressModal.open();
            String currentAddress = kraken.grab().value(Elements.Modals.AddressModal.addressField());
            if(currentAddress.equals("")) {
                message("Устанавливаем адрес доставки: " + address + "\n");
            } else {
                message("Изменяем адрес доставки:\n" + currentAddress + " >>> " + address + "\n");
                Shop.ShippingAddressModal.clearAddressField();
            }
            Shop.ShippingAddressModal.fill(address);
            Shop.ShippingAddressModal.submit();
        }

        /** Свапнуть тестовый и дефолтные адреса */
        public static void swap() {
            if (kraken.grab().currentShipAddress().equals(Addresses.Moscow.defaultAddress())) {
                set(Addresses.Moscow.testAddress());
            } else {
                set(Addresses.Moscow.testAddress());
                set(Addresses.Moscow.defaultAddress());
            }
        }
    }

    public static class PasswordRecovery {

        public static void request(UserData user) {
            request(user.getLogin());
        }

        public static void request(String email) {
            Shop.AuthModal.open();
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.proceedToPasswordRecovery();
            verboseMessage("> запрашиваем восстановление пароля для " + email);
            Shop.RecoveryModal.fillRequestForm(email);
            Shop.RecoveryModal.submitRequest();
        }

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
