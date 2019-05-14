package ru.instamart.autotests.application;

public abstract class Addresses {
    static String addressString;

    Addresses(String address) {
        addressString = address;
    }

    public static class Moscow extends Addresses {

        Moscow(String address) {
            super(address);
        }

        public static String defaultAddress() {
            return addressString = "Москва, пер Казачий 2-й, д 11 стр 2";
        }

        public static String testAddress() {
            return addressString = "Москва, Ленинский пр-кт, д 67";
        }

        public static String outOfZoneAddress() {
            return addressString = "Подольск, ул Дружбы, д 15";
        }

    }

    public static class SaintPetersburg extends Addresses {

        SaintPetersburg(String address) { super(address);}

        public static String defaultAddress() { return addressString = "Санкт-Петербург, Литейный пр-кт, д 10"; }

    }

    public static class Kazan extends Addresses {

        Kazan(String address) {
            super(address);
        }

        public static String defaultAddress() {
            return addressString = "Казань, ул Мулланура Вахитова, д 10";
        }

    }

    public static class Ekaterinburg extends Addresses {

        Ekaterinburg(String address) {
            super(address);
        }

        public static String defaultAddress() {
            return addressString = "Екатеринбург, ул Советская, д 9";
        }

    }

    public static class NizhnyNovgorod extends Addresses {

        NizhnyNovgorod(String address) {
            super(address);
        }

        public static String defaultAddress() {
            return addressString = "Нижний Новгород, тер Гск Кузнечиха, д 1";
        }

    }

    public static class RostovNaDonu extends Addresses {

        RostovNaDonu(String address) { super(address); }

        public static String defaultAddress() { return addressString = "Ростов-на-Дону, ул 2-й Пятилетки, д 23"; }

    }

    public static class Ufa extends Addresses {

        Ufa(String address) { super(address); }

        public static String defaultAddress() { return addressString = "Уфа, ул Кирова, д 1"; }

    }
}

