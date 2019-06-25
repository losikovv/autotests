package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Users;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.UserData;

public class SocialHelper extends HelperBase {

    SocialHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    // ======= ВКонтакте =======

    /** Авторизироваться/зарегистрироваться через ВК пользователем по умолчанию */
    public void submitAuthVK() {
        submitAuthVK(Users.userVK());
    }

    /** Авторизироваться/зарегистрироваться через ВК пользователем */
    public void submitAuthVK(UserData role) {
        submitAuthVK(role.getEmail(), role.getPassword());
    }

    /** Авторизироваться/зарегистрироваться через ВК с указанием данных */
    private void submitAuthVK(String email, String password) {
        kraken.perform().fillField(Elements.Social.Vkontakte.emailField(), email);
        kraken.perform().fillField(Elements.Social.Vkontakte.passwordField(), password);
        kraken.perform().click(Elements.Social.Vkontakte.submitButton());
        kraken.perform().switchToNextWindow();
        allowAccessVK();
        kraken.perform().switchToNextWindow();
        kraken.await().implicitly(1); // Ожидание авторизации через ВКонтакте
    }

    /** Запретить Инстамарту доступ к аккаунту пользователя ВК по умолчанию */
    public void denyAccessVK() {
        denyAccessVK(Users.userVK());
    }

    /** Запретить Инстамарту доступ к аккаунту пользователя ВК */
    public void denyAccessVK(UserData role) {
        denyAccessVK(role.getEmail(), role.getPassword());
    }

    /** Запретить Инстамарту доступ к аккаунту ВК с указанием данных */
    private void denyAccessVK(String email, String password) {
        kraken.get().url("https://vk.com/");
        kraken.await().implicitly(1); // Ожидание загрузки главной страницы ВК
        kraken.perform().fillField(Elements.Social.Vkontakte.index.emailField(), email);
        kraken.perform().fillField(Elements.Social.Vkontakte.index.passwordField(), password);
        kraken.perform().click(Elements.Social.Vkontakte.index.loginButton());
        kraken.await().implicitly(1); // Ожидание авторизации с главной страницы ВК

        kraken.get().url("https://vk.com/settings?act=apps");
        kraken.await().implicitly(1); // Ожидание загрузки страницы с приложениями ВК
        kraken.perform().click(Elements.Social.Vkontakte.denyAccessButton());
        kraken.perform().click(Elements.Social.Vkontakte.profileButton());
        kraken.perform().click(Elements.Social.Vkontakte.logoutButton());
    }

    /** Удалить пользователя ВК по умолчанию */
    public void deleteUserVK() throws Exception {
        AdministrationHelper.Users.deleteFirstFoundUser(Users.userVK().getEmail());
    }

    /** Разрешить Инстамарту доступ к аккаунту ВК */
    private void allowAccessVK() {
        if (kraken.detect().isElementPresent(Elements.Social.Vkontakte.allowAccessButton())) {
            kraken.perform().click(Elements.Social.Vkontakte.allowAccessButton());
            message("> разрешаем Инстамарту доступ к аккаунту ВК");
        } else {
            message("> у Инстамарта уже есть доступ к аккаунту ВК");}
    }

    /** Инициировать авторизацию/регистрацию через ВК */
    public void initAuthVK() {
        kraken.get().page("login");
        kraken.perform().click(Elements.Modals.AuthModal.vkontakte());
        kraken.await().implicitly(1); // Ожидание открытия окна ВКонтакте
        kraken.perform().switchToNextWindow();

        if(!kraken.detect().isElementPresent(Elements.Social.Vkontakte.emailField())) {
            message("⚠ Проблемы с производительностью: слишком медленно открывается окно ВК\n");
            kraken.await().implicitly(5); // Дополнительное ожидание открытия окна ВК при тормозах
        }
    }

    // ======= Facebook =======

    /** Авторизироваться через Facebook пользователем по умолчанию */
    public void submitAuthFB() {
        submitAuthFB(Users.userFB());
    }

    /** Авторизироваться через Facebook пользователем */
    public void submitAuthFB(UserData role) {
        submitAuthFB(role.getEmail(), role.getPassword());
    }

    /** Авторизироваться через Facebook с указанием данных */
    private void submitAuthFB(String email, String password) {
        kraken.perform().fillField(Elements.Social.Facebook.emailField(), email);
        kraken.perform().fillField(Elements.Social.Facebook.passwordField(), password);
        kraken.perform().click(Elements.Social.Facebook.submitButton());
        kraken.perform().switchToNextWindow();
        allowAccessFB();
        kraken.await().implicitly(1); // Ожидание авторизации через Facebook
        kraken.perform().switchToNextWindow();
        kraken.await().implicitly(1); // Ожидание авторизации через Facebook
    }

    /** Запретить Инстамарту доступ к аккаунту пользователя FB по умолчанию */
    public void denyAccessFB() {
        denyAccessFB(Users.userFB());
    }

    /** Запретить Инстамарту доступ к аккаунту пользователя FB  */
    public void denyAccessFB(UserData role) {
        denyAccessFB(role.getEmail(), role.getPassword());
    }

    /** Запретить Инстамарту доступ к аккаунту FB с указанием данных */
    private void denyAccessFB(String email, String password) {
        kraken.get().url("https://mbasic.facebook.com/");
        kraken.perform().fillField(Elements.Social.Facebook.emailField(), email);
        kraken.perform().fillField(Elements.Social.Facebook.passwordField(), password);
        kraken.perform().click(Elements.Social.Facebook.loginButton());
        kraken.await().implicitly(1); // Ожидание авторизации с главной страницы FB

        kraken.get().url("https://m.facebook.com/settings/apps/tabbed/");
        kraken.await().implicitly(1); // Ожидание загрузки страницы с приложениями FB
        kraken.perform().click(Elements.Social.Facebook.instamartButton());
        kraken.perform().click(Elements.Social.Facebook.denyAccessButton());
        kraken.perform().click(Elements.Social.Facebook.confirmDenyAccessButton());

        kraken.get().url("https://mbasic.facebook.com/");
        kraken.perform().click(Elements.Social.Facebook.logoutButton());
    }

    /** Удалить пользователя FB по умолчанию */
    public void deleteUserFB() throws Exception {
        AdministrationHelper.Users.deleteFirstFoundUser(Users.userFB().getEmail());
    }

    /** Разрешить Инстамарту доступ к аккаунту FB */
    private void allowAccessFB() {
        if (kraken.detect().isElementPresent(Elements.Social.Facebook.allowAccessButton())) {
            kraken.perform().click(Elements.Social.Facebook.allowAccessButton());
            message("> разрешаем Инстамарту доступ к аккаунту FB");
        } else {
            message("> у Инстамарта уже есть доступ к аккаунту FB");}
    }

    /** Инициировать авторизацию через Facebook */
    public void initAuthFB() {
        kraken.get().page("login");
        kraken.perform().click(Elements.Modals.AuthModal.facebook());
        kraken.await().implicitly(2); // Ожидание открытия окна Facebook
        kraken.perform().switchToNextWindow();

        if(!kraken.detect().isElementPresent(Elements.Social.Facebook.emailField())) {
            message("⚠ Проблемы с производительностью: слишком медленно открывается окно FB\n");
            kraken.await().implicitly(5); // Дополнительное ожидание открытия окна FB при тормозах
        }
    }

}
