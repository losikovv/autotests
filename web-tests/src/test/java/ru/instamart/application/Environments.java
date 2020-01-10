package ru.instamart.application;

public class Environments {

    static class sbermarket {

        public static String production() {
            return "sbermarket_production";
        }

        public static String staging() {
            return "sbermarket_staging";
        }
    }

    static class metro {

        public static String production() {
            return "metro_production";
        }

        public static String staging() {
            return "metro_staging";
        }
    }
}
