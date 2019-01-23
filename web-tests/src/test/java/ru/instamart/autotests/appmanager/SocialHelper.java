package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Users;

public class SocialHelper extends HelperBase {

    private ApplicationManager kraken;

    SocialHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    // ======= ВКонтакте =======

    /** Авторизироваться/зарегистрироваться через ВК пользователем по умолчанию */
    public void authVK() {
        authVK(Users.getCredentials("userVK").getLogin(), Users.getCredentials("userVK").getPassword());
    }

    /** Авторизироваться/зарегистрироваться через ВК с указанием данных */
    private void authVK(String email, String password) {
        initAuthVK();
        kraken.perform().fillField(Elements.Social.Vkontakte.emailField(), email);
        kraken.perform().fillField(Elements.Social.Vkontakte.passwordField(), password);
        kraken.perform().click(Elements.Social.Vkontakte.submitButton());
        kraken.perform().switchToNextWindow();
        allowAccessVK();
        kraken.perform().switchToNextWindow();
        kraken.perform().waitingFor(1); // Ожидание авторизации через ВКонтакте
    }

    /** Запретить Инстамарту доступ к аккаунту пользователя ВК по умолчанию */
    public void denyAccessVK() {
        denyAccessVK(Users.getCredentials("userVK").getLogin(), Users.getCredentials("userVK").getPassword());
    }

    /** Запретить Инстамарту доступ к аккаунту ВК с указанием данных */
    private void denyAccessVK(String email, String password) {
        kraken.get().url("https://vk.com/");
        kraken.perform().waitingFor(1); // Ожидание загрузки главной страницы ВК
        kraken.perform().fillField(Elements.Social.Vkontakte.index.emailField(), email);
        kraken.perform().fillField(Elements.Social.Vkontakte.index.passwordField(), password);
        kraken.perform().click(Elements.Social.Vkontakte.index.loginButton());
        kraken.perform().waitingFor(1); // Ожидание авторизации с главной страницы ВК

        kraken.get().url("https://vk.com/settings?act=apps");
        kraken.perform().waitingFor(1); // Ожидание загрузки страницы с приложениями ВК
        kraken.perform().click(Elements.Social.Vkontakte.denyButton());
        kraken.perform().click(Elements.Social.Vkontakte.profileButton());
        kraken.perform().click(Elements.Social.Vkontakte.logoutButton());
    }

    /** Удалить пользователя ВК по умолчанию */
    public void deleteUserVK() {
        kraken.admin().deleteFirstFoundUser(Users.getCredentials("userVK").getLogin());
    }

    /** Разрешить Инстамарту доступ к аккаунту ВК */
    private void allowAccessVK() {
        if (kraken.detect().isElementPresent(Elements.Social.Vkontakte.allowButton())) {
            kraken.perform().click(Elements.Social.Vkontakte.allowButton());
            kraken.perform().printMessage("> разрешаем Инстамарту доступ к аккаунту ВК");
        } else {
            kraken.perform().printMessage("> у Инстамарта уже есть доступ к аккаунту ВК");}
    }

    /** Инициировать авторизацию/регистрацию через ВК */
    private void initAuthVK() {
        kraken.perform().openAuthModal();
        kraken.perform().click(Elements.Site.AuthModal.vkontakte());
        kraken.perform().waitingFor(1); // Ожидание открытия окна ВКонтакте
        kraken.perform().switchToNextWindow();
    }

    // ======= Facebook =======

    /** Авторизироваться через Facebook пользователем по умолчанию */
    public void authFB() {
        authFB(Users.getCredentials("userFB").getLogin(), Users.getCredentials("userFB").getPassword());
    }

    /** Авторизироваться через Facebook с указанием данных */
    private void authFB(String email, String password) {
        initAuthFB();
        kraken.perform().fillField(Elements.Social.Facebook.emailField(), email);
        kraken.perform().fillField(Elements.Social.Facebook.passwordField(), password);
        kraken.perform().click(Elements.Social.Facebook.submitButton());
        kraken.perform().switchToNextWindow();
        kraken.perform().waitingFor(1); // Ожидание авторизации через Facebook
    }

    /** Инициировать авторизацию через Facebook */
    private void initAuthFB() {
        kraken.perform().openAuthModal();
        kraken.perform().click(Elements.Site.AuthModal.facebook());
        kraken.perform().waitingFor(1); // Ожидание открытия окна Facebook
        kraken.perform().switchToNextWindow();
    }

}
