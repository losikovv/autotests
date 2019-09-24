package ru.instamart.application;

import ru.instamart.application.models.UserData;

public abstract class Users {

    // todo убрать
    public static UserData superadmin(){
        return superadmin.getCredentials();
    }

    // todo убрать
    public static UserData superuser(){
        return superuser.getCredentials();
    }

    public static class superadmin {
        private static String role = "superadmin";
        private static String phone = "7777777777";
        private static String email = "autotestuser@instamart.ru";
        private static String password = "YortaQueojossO8";
        private static String name = "autotest" + " " + role;

        public static UserData getCredentials() { return new UserData (role, email, phone, password, name); }
    }

    // todo переделать
    public static class superuser {
        private static String role = "superuser";
        private static String email = "instatestuser@yandex.ru";
        private static String phone = "1488148814";
        private static String password = "instamart";
        private static String name = "autotest" + " " + role;

        public static UserData getCredentials() {
            return new UserData (role, email, phone, password, name);
        }
    }

    // todo переделать
    public static class userGmail {
        private static String role = "user";
        private static String email = "instamart";
        private static String password = "instamartmailtest@gmail.com";
        private static String name = "Gmail";

        public static UserData getCredentials() {
            return new UserData (role, email, password, name);
        }
    }

    public static UserData gmail() {
        return new UserData("instamartmailtest@gmail.com", "instamart");
    }

    public static UserData vkontakte() {
        return new UserData("instamart.qa@mail.ru", "hex78.Berwyn");
    }

    public static UserData facebook() {
        return new UserData("instamart.qa@mail.ru", "hex78.Berwyn");
    }

    public static UserData mailRu() {
        return new UserData("instamart.qa", "hex78.Berwyn");
    }

    public static UserData sberId() {
        return new UserData("79629422123", "hex78.Berwyn");
    }
}
