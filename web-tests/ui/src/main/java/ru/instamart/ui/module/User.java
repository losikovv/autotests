package ru.instamart.ui.module;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.UserManager;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.ui.Elements;
import ru.instamart.ui.config.WaitProperties;
import ru.instamart.ui.helper.JsHelper;
import ru.instamart.ui.manager.AppManager;

@Slf4j
public final class User extends Base {

    public User(final AppManager kraken) {
        super(kraken);
    }

    public static class Do {

        @Step("Авторизация юзером")
        public static void loginAs(UserData user) { //TODO использовать только session-юзеров
            String startURL = kraken.grab().currentURL();
            if (!startURL.equals(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH) && kraken.detect().isUserAuthorised()) {
                kraken.get().userProfilePage();
                String currentUserEmail = kraken.grab().text(Elements.UserProfile.AccountPage.email());
                log.debug("> юзер: {}", currentUserEmail);
                if (currentUserEmail == null || !currentUserEmail.equals(user.getEmail())) {
                    User.Logout.quickly();
                }
                kraken.get().url(startURL);
            }
            Auth.withEmail(user);
            if (false && kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль"))) {
                log.warn(">>> Юзер {}  не найден, регистрируем", user.getEmail());
                // костыль для stage-окружений
                if (kraken.detect().server("staging")) {
                    kraken.get().baseUrl();
                }
                Auth.withEmail(user);
                if (user.getRole().equals("admin")) {
                    User.Logout.quickly();
                    Auth.withEmail(UserManager.getDefaultAdmin());
                    Administration.Users.grantAdminPrivileges(user);
                    User.Logout.quickly();
                    Auth.withEmail(user);
                }
            }
            log.debug("> уровень прав: {}", user.getRole());
        }

        @Step("Авторизуемся в админке")
        public static void loginOnAdministration(String email, String password,String role) {
            log.debug("> авторизуемся в админке ({}/{})", email, password);
            kraken.perform().fillField(Elements.Administration.LoginPage.emailField(), email);
            kraken.perform().fillField(Elements.Administration.LoginPage.passwordField(), password);
            kraken.perform().click(Elements.Administration.LoginPage.submitButton());
            if(role.equals("superuser")){
                kraken.await().fluently(
                        ExpectedConditions.visibilityOfElementLocated(
                                Elements.Administration.LoginPage.emailFieldErrorText("Неверный email или пароль").getLocator()),
                        "Произошла авторизация в админке пользователем не имеющим доступ в админку\n");
            }else if(role.equals("superadmin")) {
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Administration.LoginPage.submitButton().getLocator()), "Не проходит авторизация в админке\n");
            }
        }

        @Step("Деавторизуемся на сайте")
        public static void logoutOnSite() {
            log.debug("> деавторизуемся на сайте");
            kraken.perform().click(Elements.Header.profileButton());
            kraken.await().shouldBeVisible(Elements.AccountMenu.logoutButton());
            kraken.perform().click(Elements.AccountMenu.logoutButton());
        }

        @Step("Деавторизуемся в админке")
        public static void logoutOnAdministration() {
            log.debug("> деавторизуемся в админке");
            kraken.perform().click(Elements.Administration.Header.logoutDropdown());
            kraken.perform().click(Elements.Administration.Header.logoutButton());
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Administration.insideContainer().getLocator()),
                    "Логаут не произошел",2);
        }


        // ======= Регистрация =======

        /**
         * Зарегистрировать тестового юзера со сгенерированными реквизитами
         */
        @Step("Регистрируем тестового юзера со сгенерированными реквизитами")
        public static UserData registration() {
            final UserData data = UserManager.getUser();
            registration(data);
            return data;
        }

        /**
         * Зарегистрировать нового юзера с реквизитами из переданного объекта UserData
         */
        @Step("Регистрируем нового юзера с реквизитами из переданного объекта UserData: {0}")
        public static void registration(UserData userData) {
            registration(userData.getName(), userData.getEmail(), userData.getPassword(),
                    userData.getPassword());
        }

        /**
         * Зарегистрировать нового юзера с указанными реквизитами
         */
        @Step("Регистрируем нового юзера с указанными реквизитами")
        public static void registration(String name, String email, String password,
                                        String passwordConfirmation) {
            log.debug("> регистрируемся ({}/{})", email, password);
            Shop.AuthModal.open();
            regSequence(name, email, password, passwordConfirmation);
            Shop.AuthModal.submitRegistration();
        }

        /**
         * Зарегистрировать нового юзера с указанными реквизитами с проверкой на тип модалки по телефону или по почте
         */
        @Step("Регистрируем нового юзера с указанными реквизитами с проверкой на тип модалки по телефону или по почте")
        public static String registration(String name, String email,
                                        String password, String passwordConfirmation,
                                        String phone,String sms) {
            Shop.AuthModal.open();
            String modalType = Shop.AuthModal.checkAutorisationModalDialog();
            if (modalType.equals("модалка с телефоном")){
                log.debug("> регистрируемся (телефон={} / смс={})", phone, sms);
            } else {
                log.debug("> регистрируемся ({}/{})", email, password);
                regSequence(name, email, password, passwordConfirmation);
                Shop.AuthModal.submitRegistration();
            }
            return modalType;
        }

        @Step("Регистрируем нового юзера по номеру телефона: {0}, без согласия на получение выгодных предложений")
        public static void registration(final String phone, final boolean messaging) {
            log.debug("> регистрируемся (телефон={})", phone);
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Modals.AuthModal.phoneNumber().getLocator()),
                    "поле для ввода мобильного телефона не отображается", WaitProperties.BASIC_TIMEOUT);
            if(!messaging) kraken.perform().setCheckbox(Elements.Modals.AuthModal.agreementCheckbox(),false);
            kraken.perform().fillFieldActionPhone(Elements.Modals.AuthModal.phoneNumber(),phone);
            kraken.perform().click(Elements.Modals.AuthModal.continueButton());
        }

        @Step("Заполняем форму регистрации без поддтверждения кода из смс: {0}")
        public static void registrationWithoutConfirmation(String phone) {
            Shop.AuthModal.open();
            log.debug("> заполняем поля формы регистрации по телефону");
            kraken.perform().fillFieldActionPhone(Elements.Modals.AuthModal.phoneNumber(),phone);
        }

        @Step("Отправляем код из смс: {0}")
        public static void sendSms(final String sms){
            log.debug("> Отправляем код из смс: {}",sms);
            kraken.perform().fillFieldAction(Elements.Modals.AuthModal.smsCode(),sms);
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.smsCode().getLocator()),
                    "Превышено время редиректа с модалки авторизации через мобилку\n",60);
            log.debug("> смс успешно прошла валидацию");
        }


        /**
         * Регистрационная последовательность с реквизитами из переданного объекта UserData
         */
        public static void regSequence(UserData userData) {
            regSequence(userData.getName(), userData.getEmail(), userData.getPassword(), userData.getPassword());
        }

        /**
         * Регистрационная последовательность с указанными реквизитами
         */
        public static void regSequence(String name, String email, String password, String passwordConfirmation) {
            Shop.AuthModal.fillRegistrationForm(name, email, password, passwordConfirmation, true);
        }

        public static class Gmail{

            public static void auth() {
                auth(UserManager.getDefaultGmailUser().getEmail(), UserManager.getDefaultGmailUser().getPassword());
            }

            @Step("Авторизуемся через Gmail")
            public static void auth(String login, String password) {
                log.debug("> переходим в веб-интерфейс Gmail...");
                kraken.get().url("https://mail.google.com/mail/u/0/h/");
                if (kraken.detect().isElementPresent(Elements.Social.Gmail.AuthForm.loginField())) {
                    log.debug("> авторизуемся в Gmail...");
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.loginField(), login);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.loginNextButton());
                    ThreadUtil.simplyAwait(1); // Ожидание загрузки страницы ввода пароля Gmail
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.passwordField(), password);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.passwordNextButton());
                    ThreadUtil.simplyAwait(1); // Ожидание авторизации в Gmail
                }
            }

            @Step("открываем последнее полученное письмо от СберМаркет")
            public static void openLastMail() {
                log.debug("> открываем последнее полученное письмо от СберМаркет");
                kraken.perform().click(Elements.EmailConfirmation.lastEmail());
                kraken.perform().click(Elements.EmailConfirmation.linkText());
            }

            @Step("Нажимаем кнопку сброса пароля в письме")
            public static void proceedToRecovery() {
                log.debug("> нажимаем кнопку сброса пароля в письме");
                kraken.perform().click(Elements.EmailConfirmation.passwordRecovery());
                //TODO Ожидание перехода из письма на сайт Инстамарт
                kraken.perform().switchToNextWindow();
            }
        }
    }

    public static class Auth {

        @Step("Авторизация по номеру телефона {0}")
        public static void withPhone(final UserData user) {
            withPhone(user.getPhone(), CoreProperties.DEFAULT_SMS);
        }

        private static void withPhone(final String phone, final String smsCode) {
            log.debug("> Заполняем поле с номером телефона");
            kraken.perform().fillFieldActionPhone(Elements.Modals.AuthModal.phoneNumber(),phone);
            kraken.perform().click(Elements.Modals.AuthModal.continueButton());

            log.debug("> Отправляем код из смс: {}", smsCode);
            kraken.perform().fillFieldAction(Elements.Modals.AuthModal.smsCode(), smsCode);
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.smsCode().getLocator()),
                    "Превышено время редиректа с модалки авторизации через мобилку\n",60);
            log.debug("> смс успешно прошла валидацию");
        }

        public static void withEmail(UserData user) {
            withEmail(user.getEmail(), user.getPassword(),user.getRole());
        }

        @Step("Авторизация через email")
        public static void withEmail(String email, String password, String role) {
            log.debug("> находимся на странице логина, авторизуемся");
            User.Do.loginOnAdministration(email, password,role);
        }

        @Step("Переходим на base url для авторизации через Vkontakte")
        public static void withVkontakte(UserData user) {
            kraken.perform().switchToNextWindow();
            kraken.perform().fillField(Elements.Social.Vkontakte.loginField(),user.getEmail());
            kraken.perform().fillField(Elements.Social.Vkontakte.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Vkontakte.submitButton());
            kraken.perform().switchToMainWindow();
        }

        @Step("Переходим на base url для авторизации через Facebook")
        public static void withFacebook(UserData user) {
            kraken.perform().switchToNextWindow();
            kraken.perform().fillField(Elements.Social.Facebook.loginField(),user.getEmail());
            kraken.perform().fillField(Elements.Social.Facebook.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Facebook.submitButton());
            kraken.perform().switchToMainWindow();
        }

        @Step("Переходим на base url для авторизации через Mail.ru")
        public static void withMailRu(UserData user) {
            kraken.perform().switchToNextWindow();
            kraken.perform().fillField(Elements.Social.MailRu.loginField(),user.getEmail());
            kraken.perform().click(Elements.Social.MailRu.nextButton());
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Social.MailRu.passwordFieldUp().getLocator()),"поле ввода пароля не появилось",WaitProperties.BASIC_TIMEOUT);
            kraken.perform().click(Elements.Social.MailRu.passwordFieldUp());
            kraken.perform().fillField(Elements.Social.MailRu.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.MailRu.submitButton());
            kraken.perform().switchToMainWindow();
        }

        @Step("Переходим на base url для авторизации через Sber ID")
        public static void withSberID(final UserData user) {
            kraken.perform().click(Elements.Social.Sber.switchLoginType());
            kraken.perform().fillField(Elements.Social.Sber.loginField(), user.getEmail());
            kraken.perform().click(Elements.Social.Sber.submitButton());
            kraken.perform().fillField(Elements.Social.Sber.passwordField(), user.getPassword());
            kraken.perform().click(Elements.Social.Sber.submitButton());
        }
    }

    public static class Logout {

        /**
         * Деавторизует, не работает с внешней деавторизацией
         */
        @Step("Логаут")
        public static void logout() {
            kraken.get().page("logout");
        }

        @Step("JS Логаут")
        public static void jsLogout() {
            JsHelper.clearSession();
            kraken.perform().refresh();
        }

        /** Быстрая деавторизация удалением кук */
        @Step("Делаем быструю деавторизацию пользователя с удалением файлов куки")
        public static void quickly() {
            log.debug("> удаляем куки...");
            AppManager.closeWebDriver();
            kraken.get().baseUrl();
            log.debug("✓ Готово");
        }

        /** Быстрая деавторизация удалением кук в админке*/
        @Step("Делаем быструю деавторизацию из админки с удалением файлов куки")
        public static void quicklyAdmin() {
            log.debug("> удаляем куки...");
            AppManager.closeWebDriver();
            kraken.get().adminPage("");
            if (kraken.detect().isInAdmin()) {
                log.debug("✓ Готово");
            }
        }
    }

    public static class PasswordRecovery {

        public static void request(UserData user) {
            request(user.getEmail());
        }

        @Step("Запрашиваем восстановление пароля для: {0}")
        public static void request(String email) {
            Shop.AuthModal.open();
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.proceedToPasswordRecovery();
            log.debug("> запрашиваем восстановление пароля для {}", email);
            Shop.RecoveryModal.fillRequestForm(email);
            Shop.RecoveryModal.submitRequest();
        }

        @Step("Восстановливаем пароль: {0} {1}")
        public static void complete(UserData user, String recoveredPassword) {
            User.Do.Gmail.auth();
            User.Do.Gmail.openLastMail();
            User.Do.Gmail.proceedToRecovery();
            log.debug("> восстановливаем пароль '{}' для {}", recoveredPassword, user.getEmail());
            Shop.RecoveryModal.fillRecoveryForm(recoveredPassword, recoveredPassword);
            Shop.RecoveryModal.submitRecovery();
        }
    }
}
