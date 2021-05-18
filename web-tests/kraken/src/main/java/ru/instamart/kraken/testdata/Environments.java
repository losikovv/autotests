package ru.instamart.kraken.testdata;

public class Environments {

    static public class sbermarket {

        public static String production() { return "sbermarket-production"; }

        public static String preprod() { return "sbermarket-preprod"; }

        public static String staging() { return "sbermarket-staging"; }

        public static String yc_staging() { return "sbermarket-yc-staging"; }
    }

    static public class metro {

        public static String production() {
            return "metro-production";
        }

        public static String staging() {
            return "metro-staging";
        }
    }

    static public class lenta {

        public static String production() {
            return "lenta-production";
        }

        public static String staging() {
            return "lenta-staging";
        }
    }
}
