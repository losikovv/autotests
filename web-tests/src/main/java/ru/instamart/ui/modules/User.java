package instamart.ui.modules;

import instamart.api.objects.v2.Address;
import instamart.core.common.AppManager;
import instamart.core.testdata.UserManager;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.helpers.WaitingHelper;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class User extends Base {

    private static final Logger log = LoggerFactory.getLogger(User.class);

    public User(final AppManager kraken) {
        super(kraken);
    }

    public static class Do {

        @Step("Авторизация юзером")
        public static void loginAs(UserData user) { //TODO использовать только session-юзеров
            String startURL = kraken.grab().currentURL();
            if (!startURL.equals(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth()) && kraken.detect().isUserAuthorised()) {
                kraken.get().userProfilePage();
                String currentUserEmail = kraken.grab().text(Elements.UserProfile.AccountPage.email());
                log.info("> юзер: {}", currentUserEmail);
                if (currentUserEmail == null || !currentUserEmail.equals(user.getLogin())) {
                    User.Logout.quickly();
                }
                kraken.get().url(startURL);
            }
            Auth.withEmail(user);
            if (/*Config.MULTI_SESSION_MODE */false && kraken.detect().isElementPresent(
                    Elements.Modals.AuthModal.errorMessage("Неверный email или пароль"))) {
                log.warn(">>> Юзер {}  не найден, регистрируем", user.getLogin());
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
            log.info("> уровень прав: {}", user.getRole());
        }
        @Step("Авторизуемся на лендинге Сбермаркет")
        private static void loginOnSberLanding(String email, String password) {
            log.info("> авторизуемся на лендинге Сбермаркет ({}/{})", email, password);
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
            log.info("> авторизуемся на сайте ({}/{})", email, password);
            Shop.AuthModal.open();
            Shop.AuthModal.switchToAuthorisationTab();
            Shop.AuthModal.fillAuthorisationForm(email, password);
            Shop.AuthModal.submit();
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()), "Не проходит авторизация на сайте\n");
        }

        @Step("Авторизуемся в админке")
        public static void loginOnAdministration(String email, String password,String role) {
            log.info("> авторизуемся в админке ({}/{})", email, password);
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
        private static void logoutOnSite() {
            log.info("> деавторизуемся на сайте");
            for (int i=0;i<60;i++){
                kraken.perform().click(Elements.Header.profileButton());
                if(kraken.getWebDriver().findElement(Elements.AccountMenu.logoutButton().getLocator()).isDisplayed()){
                    break;
                }else{
                    WaitingHelper.simply(0.3);
                }
            }
            kraken.perform().click(Elements.AccountMenu.logoutButton());
        }
        @Step("Деавторизуемся в админке")
        private static void logoutOnAdministration() {
            log.info("> деавторизуемся в админке");
            kraken.perform().click(Elements.Administration.Header.logoutButton());
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
            log.info("> регистрируемся ({}/{})", email, password);
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
            Shop.AuthModal.open();
            String modalType = Shop.AuthModal.checkAutorisationModalDialog();
            if (modalType.equals("модалка с телефоном")){
                log.info("> регистрируемся (телефон={} / смс={})", phone, sms);
                regSequenceMobile(phone, sms);
            } else {
                log.info("> регистрируемся ({}/{})", email, password);
                regSequence(name, email, password, passwordConfirmation);
                // TODO переделать на fluent-ожидание
                kraken.await().implicitly(1); // Ожидание раздизебливания кнопки подтверждения регистрации
                Shop.AuthModal.submitRegistration();
            }
            return modalType;
        }
        /**
         * Зарегистрировать нового юзера по номеру телефона
         */
        @Step("Регистрируем нового юзера по номеру телефона: {0}/{1}")
        public static void registration(String phone,String sms) {
            Shop.AuthModal.open();
            log.info("> регистрируемся (телефон={} / смс={})", phone, sms);
            regSequenceMobile(phone, sms);
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
            Shop.AuthModal.fillRegistrationFormByPhone(phone);
            if(sms!=null){
                Shop.AuthModal.sendSms(sms);
                kraken.await().fluently(
                        ExpectedConditions.invisibilityOfElementLocated(
                                Elements.Modals.AuthModal.smsCode().getLocator()),
                        "Превышено время редиректа с модалки авторизации через мобилку\n",60);
            }
       }


        public static class Gmail{

            public static void auth() {
                auth(UserManager.getDefaultGmailUser().getLogin(), UserManager.getDefaultGmailUser().getPassword());
            }

            @Step("Авторизуемся через Gmail")
            public static void auth(String login, String password) {
                log.info("> переходим в веб-интерфейс Gmail...");
                kraken.get().url("https://mail.google.com/mail/u/0/h/");
                if (kraken.detect().isElementPresent(Elements.Social.Gmail.AuthForm.loginField())) {
                    log.info("> авторизуемся в Gmail...");
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.loginField(), login);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.loginNextButton());
                    WaitingHelper.simply(1); // Ожидание загрузки страницы ввода пароля Gmail
                    kraken.perform().fillField(Elements.Social.Gmail.AuthForm.passwordField(), password);
                    kraken.perform().click(Elements.Social.Gmail.AuthForm.passwordNextButton());
                    WaitingHelper.simply(1); // Ожидание авторизации в Gmail
                }
            }

            @Step("открываем последнее полученное письмо от СберМаркет")
            public static void openLastMail() {
                log.info("> открываем последнее полученное письмо от СберМаркет");
                kraken.perform().click(Elements.EmailConfirmation.lastEmail());
                kraken.perform().click(Elements.EmailConfirmation.linkText());
            }

            @Step("Нажимаем кнопку сброса пароля в письме")
            public static void proceedToRecovery() {
                log.info("> нажимаем кнопку сброса пароля в письме");
                kraken.perform().click(Elements.EmailConfirmation.passwordRecovery());
                kraken.await().implicitly(1); // Ожидание перехода из письма на сайт Инстамарт
                kraken.perform().switchToNextWindow();
            }
        }
    }

    public static class Auth {

        public static void withEmail(UserData user) {
            withEmail(user.getLogin(), user.getPassword(),user.getRole());
        }

        @Step("Авторизация через email")
        public static void withEmail(String email, String password, String role) {
            if (kraken.detect().isOnAdminLoginPage()) {
                log.info("> находимся на странице логина, авторизуемся");
                User.Do.loginOnAdministration(email, password,role);
            } else {
                if (!kraken.detect().isUserAuthorised()) {
                    User.Do.loginOnSite(email, password);
                } else {
                    log.info("> пропускаем авторизацию, уже авторизованы");
                }
            }
        }

        @Step("Переходим на base url для авторизации через Vkontakte")
        public static void withVkontakte(UserData user) {
            if (kraken.detect().isInAdmin()) {
                log.info("> переходим на base url для авторизации через Vkontakte");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitVkontakteButton();
            WaitingHelper.simply(2); // Ожидание открытия фрейма авторизации Vkontakte
            kraken.perform().switchToNextWindow();

            kraken.perform().fillField(Elements.Social.Vkontakte.loginField(),user.getLogin());
            kraken.perform().fillField(Elements.Social.Vkontakte.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Vkontakte.submitButton());
            WaitingHelper.simply(1); // Ожидание авторизации через Vkontakte

            kraken.await().fluentlyWithWindowsHandler(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Modals.AuthModal.popup().getLocator()));
        }

        @Step("Переходим на base url для авторизации через Facebook")
        public static void withFacebook(UserData user) {
            if (kraken.detect().isInAdmin()) {
                log.info("> переходим на base url для авторизации через Facebook");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitFacebookButton();
            WaitingHelper.simply(2); // Ожидание открытия фрейма авторизации Facebook
            kraken.perform().switchToNextWindow();

            kraken.perform().fillField(Elements.Social.Facebook.loginField(),user.getLogin());
            kraken.perform().fillField(Elements.Social.Facebook.passwordField(),user.getPassword());
            kraken.perform().click(Elements.Social.Facebook.submitButton());
            WaitingHelper.simply(1); // Ожидание авторизации через Facebook

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
                log.info("> переходим на base url для авторизации через Mail.ru");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitMailRuButton();
            WaitingHelper.simply(5); // Ожидание открытия фрейма авторизации Mail.ru
            kraken.perform().switchToNextWindow();

            kraken.perform().fillField(Elements.Social.MailRu.loginField(),user.getLogin());
            kraken.perform().click(Elements.Social.MailRu.nextButton());
            WaitingHelper.simply(2);
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
                log.info("> переходим на base url для авторизации через Sber ID");
                kraken.get().baseUrl();
            }
            Shop.AuthModal.open();
            Shop.AuthModal.hitSberIdButton();
            //TODO тест сломан, отладить на новом стейдже
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Social.Sber.sberButtonsSection().getLocator()));
            WebElement parent = kraken.getWebDriver().findElement(Elements.Social.Sber.sberButtonsSection().getLocator());
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
                log.info("> логаут...");
                if (kraken.detect().isInAdmin()) {
                    User.Do.logoutOnAdministration();
                } else {
                    User.Do.logoutOnSite();
                }
                kraken.await().implicitly(1); // Ожидание деавторизации и подгрузки лендинга
                if (!kraken.detect().isUserAuthorised()) {
                    log.info("✓ Готово");
                }
            } else {
                log.info("> пропускаем деавторизацию, уже разлогинены");
            }
        }

        /** Быстрая деавторизация прямым переходом на /logout */
        @Step("Деавторизация прямым переходом на /logout")
        public static void quick() {
            log.info("> быстрый логаут...");
            kraken.get().page("logout");
            WaitingHelper.simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                log.info("✓ Готово");
            }
        }

        /** Быстрая деавторизация удалением кук */
        @Step("Делаем быструю деавторизацию пользователя с удалением файлов куки")
        public static void quickly() {
            log.info("> удаляем куки...");
            kraken.getWebDriver().manage().deleteAllCookies();
            kraken.get().baseUrl();
            //kraken.await().simply(1); // Ожидание деавторизации и подгрузки лендинга
            if (kraken.detect().isOnLanding()) {
                log.info("✓ Готово");
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
                if(currentAddress.equals("")) log.info("> устанавливаем адрес доставки: {}", address);
                else Shop.ShippingAddressModal.clearAddressField();
            } else {
                log.info("> изменяем адрес доставки: {} >>> {}", currentAddress, address);
                Shop.ShippingAddressModal.clearAddressField();
            }
            Shop.ShippingAddressModal.fill(address);
            if(submit){
                WaitingHelper.simply(2);
                kraken.await().fluently(
                        ExpectedConditions.elementToBeClickable(
                                Elements.Modals.AddressModal.submitButton().getLocator()),
                        "Кнопка сохранить не активна\n"
                );
                Shop.ShippingAddressModal.submit();
            }
        }

        /** Ищем магазины по установленному адресу */
        @Step("Поиск магазинов по адресу доставки: {0}")
        public static void searchShopsByAddress(String address){
            kraken.await().fluently(
                    ExpectedConditions
                            .elementToBeClickable(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1).getLocator()),
                    "кнопка выбора ретейлера недоступна");
            kraken.perform().click(Elements.Landings.SbermarketLanding.MainBlock.Stores.button(1));
            Shop.ShippingAddressModal.open();
            Shop.ShippingAddressModal.clearAddressField();
            Shop.ShippingAddressModal.fill(address);
            Shop.ShippingAddressModal.findShops();
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
            log.info("> запрашиваем восстановление пароля для {}", email);
            Shop.RecoveryModal.fillRequestForm(email);
            Shop.RecoveryModal.submitRequest();
        }

        @Step("Восстановливаем пароль: {0} {1}")
        public static void complete(UserData user, String recoveredPassword) {
            User.Do.Gmail.auth();
            User.Do.Gmail.openLastMail();
            User.Do.Gmail.proceedToRecovery();
            log.info("> восстановливаем пароль '{}' для {}", recoveredPassword, user.getLogin());
            Shop.RecoveryModal.fillRecoveryForm(recoveredPassword, recoveredPassword);
            Shop.RecoveryModal.submitRecovery();
        }
    }
}
