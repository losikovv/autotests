package ru.instamart.autotests.application;

import ru.instamart.autotests.testdata.Generate;

public abstract class Addresses {
    static String addressString;

    Addresses(String address) {
        addressString = address;
    }

    public static class Moscow extends Addresses {

        Moscow(String address) {
            super(address);
        }

        public static String testAddress() {
            return addressString = "Москва, ул Тестовская, д 10";
        }

        public static String defaultAddress() {
            return addressString = "Москва, пер Казачий 2-й, д 11 стр 2";
        }

        public static String outOfZoneAddress() {
            return addressString = "Москва, ул Лосиноостровская, д 2 стр 1";
        }

        public static String randomAddress() {
            return Generate.randomAddress("moscow");
        }
    }

    public static class Kazan extends Addresses {

        Kazan(String address) {
            super(address);
        }

        public static String testAddress() {
            return addressString = "Казань, ул Танковая, д 30";
        }

        public static String defaultAddress() {
            return addressString = "Казань, ул Мулланура Вахитова, д 10";
        }

        public static String outOfZoneAddress() {
            return addressString = "Казань, ул Беломорская, д 1";
        }

        public static String randomAddress() {
            return Generate.randomAddress("kazan");
        }
    }

    public static class Ekaterinburg extends Addresses {

        Ekaterinburg(String address) {
            super(address);
        }

        public static String testAddress() {
            return addressString = "Екатеринбург, ул Малышева, д 24";
        }

        public static String defaultAddress() {
            return addressString = "Екатеринбург, ул Щорса, д 96";
        }

        public static String outOfZoneAddress() {
            return addressString = "Екатеринбург, ул Коммунистическая, д 103";
        }

        public static String randomAddress() {
            return Generate.randomAddress("ekaterinburg");
        }
    }

}

