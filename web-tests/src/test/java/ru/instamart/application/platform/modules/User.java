package ru.instamart.application.platform.modules;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.application.lib.Elements;
import ru.instamart.application.lib.Users;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.models.UserData;
import ru.instamart.testdata.generate;

import static ru.instamart.application.Config.CoreSettings.multiSessionMode;

public class User extends Base {

    public User(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public static class Do {

        /**
         * Залогиниться юзером с указанной ролью
         */
        public static void loginAs(UserData user) { //TODO использовать только session-юзеров
            String startURL = kraken.grab().currentURL();
            if (!startURL.equals(fullBaseUrl) && kraken.detect().isUserAuthorised()) {
                kraken.get().profilePage();
                String currentUserEmail = kraken.grab().text(Elements.UserProfile.AccountPage.email());
                message("Юзер: " + currentUserEmail);
                if (currentUserEmail == null || !currentUserEmail.equals(user.getEmail())) {
                    quickLogout();
                }
                kraken.get().url(startURL);
            }
            login(user);
            if (multiSessionMode && kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль"))) {
                message(">>> Юзер " + user.getEmail() + " не найден, регистрируем\n");
                // костыль для stage-окружений
                if (kraken.detect().server("staging")) {
                    kraken.get().baseUrl();
                }
                login(user);
                if (user.getRole().equals("admin")) {
                    quickLogout();
                    login(Users.superadmin());
                    Administration.Users.grantAdminPrivileges(user);
                    quickLogout();
                    login(user);
                }
            }
            message("Уровень прав: " + user.getRole() + "\n");
        }

        /**
         * Залогиниться с реквизитами из переданного объекта UserData
         */
        public static void login(UserData userData) {
            login(userData.getEmail(), userData.getPassword());
        }

        /**
         * Залогиниться с указанными реквизитами
         */
        public static void login(String email, String password) {
            if (kraken.detect().isInAdmin()) {
                loginOnAdministration(email, password);
            } else {
                if (!kraken.detect().isUserAuthorised()) {
                    loginOnSite(email, password);
                } else {
                    message("Пропускаем авторизацию, уже авторизованы");
                }
            }
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

        /**
         * Деавторизоваться
         */
        public static void logout() {
            verboseMessage("Логаут...");
            if (kraken.detect().isInAdmin()) {
                kraken.perform().click(Elements.Administration.Header.logoutButton());
            } else {
                kraken.perform().click(Elements.Header.profileButton());
                kraken.perform().click(Elements.AccountMenu.logoutButton());
            }
            kraken.await().implicitly(1); // Ожидание деавторизации и подгрузки лендинга
            if (!kraken.detect().isUserAuthorised()) {
                verboseMessage("✓ Готово\n");
            }
        }

        public static void quickLogout() {
            verboseMessage("Быстрый логаут...");
            kraken.get().page("logout");
            kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                verboseMessage("✓ Готово\n");
            }
        }


        // ======= Регистрация =======

        /**
         * Зарегистрировать тестового юзера со сгенерированными реквизитами
         */
        public static void registration() {
            registration(generate.testCredentials("user"));
        }

        /**
         * Зарегистрировать нового юзера с реквизитами из переданного объекта UserData
         */
        public static void registration(UserData userData) {
            registration(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword());
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
            regSequence(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword());
        }

        /**
         * Регистрационная последовательность с реквизитами из переданного объекта UserData
         */
        public static void regSequence(UserData userData, boolean agreement) {
            Shop.AuthModal.switchToRegistrationTab();
            Shop.AuthModal.fillRegistrationForm(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword(), agreement);
        }

        /**
         * Регистрационная последовательность с указанными реквизитами
         */
        public static void regSequence(String name, String email, String password, String passwordConfirmation) {
            Shop.AuthModal.switchToRegistrationTab();
            Shop.AuthModal.fillRegistrationForm(name, email, password, passwordConfirmation, true);
        }


        // ======= Работа с Gmail =======

        /**
         * Авторизоваться в гугл почте с определённой ролью
         */
        public static void authGmail(UserData role) {
            authGmail(Users.userGmail().getEmail(), Users.userGmail().getPassword());
        }

        /**
         * Авторизоваться в гугл почте
         */
        public static void authGmail(String gmail, String password) {
            verboseMessage("> авторизовываемся в гугл почте...");
            kraken.get().url("https://mail.google.com/mail/u/0/h/");
            kraken.perform().fillField(Elements.GMail.AuthPage.idField(), gmail);
            kraken.perform().click(Elements.GMail.AuthPage.idNextButton());
            kraken.await().implicitly(1); // Ожидание загрузки страницы ввода пароля Gmail
            kraken.perform().fillField(Elements.GMail.AuthPage.passwordField(), password);
            kraken.perform().click(Elements.GMail.AuthPage.passwordNextButton());
            kraken.await().implicitly(1); // Ожидание авторизации в Gmail
        }

        /**
         * Открыть крайнее письмо в цепочке писем от Инстамарт
         */
        public static void openLastGmail() {
            verboseMessage("> открываем крайнее письмо от Инстамарт");
            kraken.perform().click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Instamart'])[1]/following::span[1]"));
            kraken.perform().click(By.linkText("- Показать цитируемый текст -"));
        }

        /**
         * Нажать кнопку сброса пароля в письме
         */
        public static void clickRecoveryInMail() {
            verboseMessage("> нажимаем кнопку сброса пароля в письме");
            kraken.perform().click(By.linkText("СБРОСИТЬ ПАРОЛЬ"));
            kraken.await().implicitly(1); // Ожидание перехода из письма на сайт Инстамарт
            kraken.perform().switchToNextWindow();
        }

        /**
         * Запросить восстановление пароля для указанной роли
         */
        public static void requestPasswordRecovery(UserData role) {
            requestPasswordRecovery(role.getEmail());
        }

        /**
         * Запросить восстановление пароля
         */
        public static void requestPasswordRecovery(String email) {
            Shop.AuthModal.open();
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.proceedToPasswordRecovery();
            verboseMessage("> запрашиваем восстановление пароля для " + email);
            Shop.RecoveryModal.fillRequestForm(email);
            Shop.AuthModal.submit();
        }
    }
}
