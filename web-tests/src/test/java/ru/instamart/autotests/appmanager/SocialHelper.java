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

    /** Авторизироваться через ВК пользователем по умолчанию */
    public void authVK() {
        authVK(Users.getCredentials("userVK").getLogin(), Users.getCredentials("userVK").getPassword());
    }

    /** Авторизироваться через ВК с указанием данных */
    private void authVK(String email, String password) {
        initAuthVK();
        kraken.perform().fillField(Elements.Social.Vkontakte.emailField(), email);
        kraken.perform().fillField(Elements.Social.Vkontakte.passwordField(), password);
        kraken.perform().click(Elements.Social.Vkontakte.submitButton());
        kraken.perform().switchToNextWindow();
    }

    /** Инициировать авторизацию через ВК */
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
    }

    /** Инициировать авторизацию через Facebook */
    private void initAuthFB() {
        kraken.perform().openAuthModal();
        kraken.perform().click(Elements.Site.AuthModal.facebook());
        kraken.perform().waitingFor(1); // Ожидание открытия окна Facebook
        kraken.perform().switchToNextWindow();
    }

}
