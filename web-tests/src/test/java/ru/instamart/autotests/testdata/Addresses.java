package ru.instamart.autotests.testdata;



    // Тестовые адреса доставки



public class Addresses {

    public static String get (String type) {
        final String address;
        switch (type) {

            case "default":
                address = "Москва, ул Тестовская, д 1";
                break;
            case "non-default":
                address = "Москва, ул Люсиновская, д 12";
                break;
            case "out-of-zone":
                address = "Москва, ул Лосиноостровская, д 2 стр 1";
                break;
            case "office":
                address = "Москва, пер Казачий 2-й, д 11 стр 2";
                break;

            case "default-kazan":
                address = "Казань, ул Мулланура Вахитова, д 10";
                break;
            case "non-default-kazan":
                address = "Казань, ул Танковая, д 30";
                break;
            case "out-of-zone-kazan":
                address = "Казань, ул Беломорская, д 1";
                break;
            default: address = null;

        }
        return address;
    }

}
