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

    /**  ВКонтакте */
    public static class Vkontakte {

        /** Инициировать авторизацию/регистрацию через ВК */
        public static void initAuth() {
            kraken.get().page("login");
            kraken.perform().click(Elements.Modals.AuthModal.vkontakte());
            kraken.await().implicitly(1); // Ожидание открытия окна ВКонтакте
            kraken.perform().switchToNextWindow();

            // TODO заменить на fluent-ожидание
            if(!kraken.detect().isElementPresent(Elements.Social.Vkontakte.emailField())) {
                message("⚠ Проблемы с производительностью: слишком медленно открывается окно ВК\n");
                kraken.await().implicitly(5); // Дополнительное ожидание открытия окна ВК при тормозах
            }
        }

        /** Авторизироваться/зарегистрироваться через ВК пользователем по умолчанию */
        public static void submitAuth() {
            submitAuth(Users.userVK());
        }

        /** Авторизироваться/зарегистрироваться через ВК пользователем */
        public static void submitAuth(UserData role) {
            submitAuth(role.getEmail(), role.getPassword());
        }

        /** Авторизироваться/зарегистрироваться через ВК с указанием данных */
        private static void submitAuth(String email, String password) {
            kraken.perform().fillField(Elements.Social.Vkontakte.emailField(), email);
            kraken.perform().fillField(Elements.Social.Vkontakte.passwordField(), password);
            kraken.perform().click(Elements.Social.Vkontakte.submitButton());
            kraken.perform().switchToNextWindow();
            allowAccess();
            kraken.perform().switchToNextWindow();
            kraken.await().implicitly(1); // Ожидание авторизации через ВКонтакте
        }

        /** Запретить Инстамарту доступ к аккаунту пользователя ВК по умолчанию */
        public static void denyAccess() {
            denyAccess(Users.userVK());
        }

        /** Запретить Инстамарту доступ к аккаунту пользователя ВК */
        public static void denyAccess(UserData role) {
            denyAccess(role.getEmail(), role.getPassword());
        }

        /** Запретить Инстамарту доступ к аккаунту ВК с указанием данных */
        private static void denyAccess(String email, String password) {
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
        public static void deleteUser() {
            AdministrationHelper.Users.deleteFirstFoundUser(Users.userVK().getEmail());
        }

        /** Разрешить Инстамарту доступ к аккаунту ВК */
        private static void allowAccess() {
            if (kraken.detect().isElementPresent(Elements.Social.Vkontakte.allowAccessButton())) {
                kraken.perform().click(Elements.Social.Vkontakte.allowAccessButton());
                message("> разрешаем Инстамарту доступ к аккаунту ВК");
            } else {
                message("> у Инстамарта уже есть доступ к аккаунту ВК");}
        }
    }

    /**  Facebook */
    public static class Facebook {

        /** Инициировать авторизацию через Facebook */
        public static void initAuth() {
            kraken.get().page("login");
            kraken.perform().click(Elements.Modals.AuthModal.facebook());
            kraken.await().implicitly(2); // Ожидание открытия окна Facebook
            kraken.perform().switchToNextWindow();

            // TODO заменить на fluent-ожидание
            if(!kraken.detect().isElementPresent(Elements.Social.Facebook.emailField())) {
                message("⚠ Проблемы с производительностью: слишком медленно открывается окно FB\n");
                kraken.await().implicitly(5); // Дополнительное ожидание открытия окна FB при тормозах
            }
        }

        /** Авторизироваться через Facebook пользователем по умолчанию */
        public static void submitAuth() {
            submitAuth(Users.userFB());
        }

        /** Авторизироваться через Facebook пользователем */
        public static void submitAuth(UserData role) {
            submitAuth(role.getEmail(), role.getPassword());
        }

        /** Авторизироваться через Facebook с указанием данных */
        private static void submitAuth(String email, String password) {
            kraken.perform().fillField(Elements.Social.Facebook.emailField(), email);
            kraken.perform().fillField(Elements.Social.Facebook.passwordField(), password);
            kraken.perform().click(Elements.Social.Facebook.submitButton());
            kraken.perform().switchToNextWindow();
            allowAccess();
            kraken.await().implicitly(1); // Ожидание авторизации через Facebook
            kraken.perform().switchToNextWindow();
            kraken.await().implicitly(1); // Ожидание авторизации через Facebook
        }

        /** Запретить Инстамарту доступ к аккаунту пользователя FB по умолчанию */
        public static void denyAccess() {
            denyAccess(Users.userFB());
        }

        /** Запретить Инстамарту доступ к аккаунту пользователя FB  */
        public static void denyAccess(UserData role) {
            denyAccess(role.getEmail(), role.getPassword());
        }

        /** Запретить Инстамарту доступ к аккаунту FB с указанием данных */
        private static void denyAccess(String email, String password) {
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
        public static void deleteUser() {
            AdministrationHelper.Users.deleteFirstFoundUser(Users.userFB().getEmail());
        }

        /** Разрешить Инстамарту доступ к аккаунту FB */
        private static void allowAccess() {
            if (kraken.detect().isElementPresent(Elements.Social.Facebook.allowAccessButton())) {
                kraken.perform().click(Elements.Social.Facebook.allowAccessButton());
                message("> разрешаем Инстамарту доступ к аккаунту FB");
            } else {
                message("> у Инстамарта уже есть доступ к аккаунту FB");}
        }
    }
}
