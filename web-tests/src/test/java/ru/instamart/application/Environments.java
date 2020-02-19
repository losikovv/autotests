package ru.instamart.application;

public class Environments {

    static class sbermarket {

        public static String production() { return "sbermarket-production"; }

        public static String staging() { return "sbermarket-staging"; }
    }

    static class metro {

        public static String production() {
            return "metro-production";
        }

        public static String staging() {
            return "metro-staging";
        }
    }

    static class lenta {

        public static String production() {
            return "lenta-production";
        }

        public static String staging() {
            return "lenta-staging";
        }
    }
}
