package ru.instamart.application;

import ru.instamart.application.models.UserData;

public abstract class Users {

    public static UserData superadmin(){
        return superadmin.getCredentials();
    }

    public static UserData superuser(){
        return superuser.getCredentials();
    }

    public static UserData userGmail(){ return userGmail.getCredentials(); }

    public static UserData userVK(){ return userVK.getCredentials(); }

    public static UserData userFB(){ return userFB.getCredentials(); }

    public static class superadmin {
        private static String role = "superadmin";
        private static String phone = "7777777777";
        private static String email = "autotestuser@instamart.ru";
        private static String password = "YortaQueojossO8";
        private static String name = "autotest" + " " + role;

        public static UserData getCredentials() { return new UserData (role, email, phone, password, name); }
    }

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

    public static class userGmail {
        private static String role = "user";
        private static String email = "instamart";
        private static String password = "instamartmailtest@gmail.com";
        private static String name = "Gmail";

        public static UserData getCredentials() {
            return new UserData (role, email, password, name);
        }
    }

    public static class userVK {
        private static String role = "user";
        private static String email= "instamart.qa@mail.ru";
        private static String password = "hex78.Berwyn";
        private static String name = "Instamart QA";

        public static UserData getCredentials() {
            return new UserData (role, email, password, name);
        }
    }

    public static class userFB {
        private static String role = "user";
        private static String email= "instamart.qa@mail.ru";
        private static String password = "hex78.Berwyn";
        private static String name = "Instamart QA";

        public static UserData getCredentials() {
            return new UserData (role, email, password, name);
        }
    }


    public static UserData sber(){
        return new UserData(
                "user",
                "",
                "",
                "21visetu",
                "79629422123");
    }
}
