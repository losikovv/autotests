package ru.instamart.autotests.testdata;



    // Тестовые адреса доставки



public abstract class Addresses {
    static String addressString;

    public static String get() {
        return addressString;
    }

    public static class Moscow extends Addresses {

        Moscow() {
            addressString = addressString;
        }

        public static Moscow testAddress() {
            addressString = "Москва, ул Тестовская, д 1";
            return new Moscow();
        }

        public static Moscow defaultAddress() {
            addressString = "Москва, пер Казачий 2-й, д 11 стр 2";
            return new Moscow();
        }

        public static Moscow outOfZoneAddress() {
            addressString = "Москва, ул Лосиноостровская, д 2 стр 1";
            return new Moscow();
        }

        // TODO public static Moscow randomAddress() {
        // Generate.randomAddress("moscow")
        // }
    }

    public static class Kazan extends Addresses {

        Kazan() {
            addressString = addressString;
        }

        public static Kazan testAddress() {
            addressString = "Казань, ул Танковая, д 30";
            return new Kazan();
        }

        public static Kazan defaultAddress() {
            addressString = "Казань, ул Мулланура Вахитова, д 10";
            return new Kazan();
        }

        public static Kazan outOfZoneAddress() {
            addressString = "Казань, ул Беломорская, д 1";
            return new Kazan();
        }

        // TODO public static Moscow randomAddress() {
        // Generate.randomAddress("kazan")
        // }
    }
}







    /*
    public static String get (String type) {
        final String addressString;
        switch (type) {

            case "default":
                addressString = "Москва, ул Тестовская, д 1";
                break;
            case "non-default":
                addressString = "Москва, ул Люсиновская, д 12";
                break;
            case "out-of-zone":
                addressString = "Москва, ул Лосиноостровская, д 2 стр 1";
                break;
            case "office":
                addressString = "Москва, пер Казачий 2-й, д 11 стр 2";
                break;
            // TODO case "random":
                // TODO addressString = Generate.randomAddress("moscow");
            // TODO break;

            case "default-kazan":
                addressString = "Казань, ул Мулланура Вахитова, д 10";
                break;
            case "non-default-kazan":
                addressString = "Казань, ул Танковая, д 30";
                break;
            case "out-of-zone-kazan":
                addressString = "Казань, ул Беломорская, д 1";
                break;
            // TODO case "random-kazan":
                // TODO addressString = Generate.randomAddress("kazan");
            // TODO break;

                default: addressString = null;

        }
        return addressString;
    }

    */

//}
