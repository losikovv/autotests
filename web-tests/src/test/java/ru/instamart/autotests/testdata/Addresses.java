package ru.instamart.autotests.testdata;



// Тестовые адреса доставки



public abstract class Addresses {
    static String addressString;

    Addresses(String address) {
        addressString = address;
    }

    public static String get(String address) {
        return addressString;
    }


    public static class Moscow extends Addresses {

        Moscow(String address) {
            super(address);
        }

        public static String testAddress() {
            return addressString = "Москва, ул Тестовская, д 1";
        }

        public static String defaultAddress() {
            return addressString = "Москва, пер Казачий 2-й, д 11 стр 2";
        }

        public static String outOfZoneAddress() {
            return addressString = "Москва, ул Лосиноостровская, д 2 стр 1";
        }

        // TODO public static Moscow randomAddress() { Generate.randomAddress("moscow") }
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

        // TODO public static K randomAddress() { Generate.randomAddress("kazan") }
    }
}

