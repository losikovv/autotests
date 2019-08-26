package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.generate;

import static ru.instamart.autotests.application.Config.CoreSettings.multiSessionMode;

public class User extends HelperBase {

    User(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
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
                if (kraken.environment.getServer().equals("staging")) {
                    kraken.get().baseUrl();
                }
                login(user);
                if (user.getRole().equals("admin")) {
                    quickLogout();
                    login(Users.superadmin());
                    AdministrationHelper.Users.grantAdminPrivileges(user);
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
            openAuthModal();
            authSequence(email, password);
            sendForm();
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
         * Авторизационная последовательность с реквизитами из переданного объекта UserData
         */
        public static void authSequence(UserData role) {
            authSequence(role.getEmail(), role.getPassword());
        }

        /**
         * Авторизационная последовательность с указанными реквизитами
         */
        public static void authSequence(String email, String password) {
            switchToAuthorisationTab();
            fillAuthorisationForm(email, password);
        }

        /**
         * Деавторизоваться
         */
        public static void logout() {
            if (kraken.detect().isInAdmin()) {
                kraken.perform().click(Elements.Administration.Header.logoutButton());
            } else {
                kraken.perform().click(Elements.Header.profileButton());
                kraken.perform().click(Elements.AccountMenu.logoutButton());
            }
            kraken.await().implicitly(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                verboseMessage("Логаут\n");
            }
        }

        /**
         * Деавторизоваться быстро по прямой ссылке
         */
        public static void quickLogout() {
            kraken.get().page("logout");
            kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                message("Быстрый логаут\n");
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
            openAuthModal();
            regSequence(name, email, password, passwordConfirmation);
            // TODO переделать на fluent-ожидание
            kraken.await().implicitly(1); // Ожидание раздизебливания кнопки подтверждения регистрации
            sendForm();
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
            switchToRegistrationTab();
            fillRegistrationForm(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword(), agreement);
        }

        /**
         * Регистрационная последовательность с указанными реквизитами
         */
        public static void regSequence(String name, String email, String password, String passwordConfirmation) {
            switchToRegistrationTab();
            fillRegistrationForm(name, email, password, passwordConfirmation, true);
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

        // ======= Методы модалки авторизации/регистрации =======

        /**
         * Открыть форму авторизации/регистрации
         */
        public static void openAuthModal() {
            if (!kraken.detect().isAuthModalOpen()) {
                verboseMessage("> открываем модалку авторизации");
                if (kraken.detect().isOnLanding()) {
                    kraken.perform().click(Elements.Landing.MainBlock.loginButton());
                } else {
                    kraken.perform().click(Elements.Header.loginButton());
                }
                kraken.await().implicitly(1); // Ожидание открытия модалки авторизации/регистрации
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(
                                Elements.Modals.AuthModal.popup().getLocator()),
                        "\n> Превышено время ожидания открытия модалки авторизации/регистрации"
                );
            }
        }

        /**
         * Переключиться на вкладку регистрации
         */
        private static void switchToRegistrationTab() {
            verboseMessage("> переключаемся на вкладку регистрации");
            kraken.perform().click(Elements.Modals.AuthModal.registrationTab());
        }

        /**
         * Заполнить поля формы регистрации
         */
        private static void fillRegistrationForm(String name, String email, String password, String passwordConfirmation, boolean agreementConfirmation) {
            verboseMessage("> заполняем поля формы регистрации");
            kraken.perform().fillField(Elements.Modals.AuthModal.nameField(), name);
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordConfirmationField(), passwordConfirmation);
            if (!agreementConfirmation) {
                kraken.perform().click(Elements.Modals.AuthModal.agreementCheckbox());
            }
        }

        /**
         * Отправить форму
         */
        public static void sendForm() {
            verboseMessage("> отправляем форму\n");
            kraken.perform().click(Elements.Modals.AuthModal.submitButton());
            kraken.await().implicitly(2); // Ожидание авторизации
        }

        /**
         * Закрыть форму авторизации/регистрации
         */
        public static void closeAuthModal() {
            kraken.perform().click(Elements.Modals.AuthModal.closeButton());
            kraken.await().implicitly(1); // Ожидание закрытия модалки авторизации
        }

        /**
         * Переключиться на вкладку авторизации
         */
        private static void switchToAuthorisationTab() {
            try {
                verboseMessage("> переключаемся на вкладку авторизации");
                kraken.perform().click(Elements.Modals.AuthModal.authorisationTab());
            } catch (ElementNotInteractableException e) {
                debugMessage(" > что-то пошло не так, пробуем ещё раз...");
                kraken.perform().click(Elements.Modals.AuthModal.authorisationTab());
            }
        }

        /**
         * Заполнить поля формы авторизации
         */
        private static void fillAuthorisationForm(String email, String password) {
            verboseMessage("> заполняем поля формы авторизации...");
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            kraken.perform().fillField(Elements.Modals.AuthModal.passwordField(), password);
        }

        /**
         * Перейти в форму восстановления пароля
         */
        private static void proceedToPasswordRecovery() {
            verboseMessage("> переходим в форму восстановления пароля");
            kraken.perform().click(Elements.Modals.AuthModal.forgotPasswordButton());
        }

        /**
         * Запросить восстановление пароля для указанной роли
         */
        public static void recoverPasswordAs(UserData role) {
            recoverPassword(role.getEmail());
        }

        /**
         * Запросить восстановление пароля
         */
        public static void recoverPassword(String email) {
            openAuthModal();
            switchToAuthorisationTab();
            proceedToPasswordRecovery();
            verboseMessage("> запрашиваем восстановление пароля для " + email);
            kraken.perform().fillField(Elements.Modals.AuthModal.emailField(), email);
            sendForm();
            kraken.await().implicitly(1); // Ожидание раздизабливания кнопки подтверждения восстановления пароля
        }

        /**
         * Придумать новый пароль для восстановления пароля
         */
        public static void submitRecovery(String password, String passwordConfirmation) {
            verboseMessage("> задаем новый пароль...\n");
            kraken.perform().fillField(Elements.PasswordRecovery.RecoveryModal.passwordField(), password);
            kraken.perform().fillField(Elements.PasswordRecovery.RecoveryModal.passwordConfirmationField(), passwordConfirmation);
            kraken.perform().click(Elements.PasswordRecovery.RecoveryModal.submitButton());
        }
    }
}
