package instamart.ui.common.lib;

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
            //return addressString = "Москва, пр-кт Мира, д 211 стр 1";
            return addressString = "проспект Мира, 211к1, Москва";
        }

        public static String testAddress() {
            return addressString = "Ленинский проспект, 67, Москва";
            //return addressString = "Москва, Ленинский пр-кт, д 67";
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

    public static class Krasnodar extends Addresses {

        Krasnodar(String address) { super(address); }

        public static String defaultAddress() { return addressString = "Краснодар, ул Аграрная, д 1"; }
    }

    public static class Samara extends Addresses {

        Samara(String address) { super(address); }

        public static String defaultAddress() { return addressString = "Самара, ул Ленинская, д 10"; }
    }

    public static class Voronezh extends Addresses {

        Voronezh(String address) {super(address); }

        public static String defaultAddress() { return addressString = "Воронеж, ул Ленина, д 12"; }
    }

    public static class Omsk extends Addresses {

        Omsk(String address) {super(address); }

        public static String defaultAddress() { return addressString = "Омск, ул Омская, д 110"; }
    }

    public static class Volgograd extends Addresses {

        Volgograd(String address) {super(address); }

        public static String defaultAddress() { return addressString = "Волгоград, ул Абаканская, д 11"; }
    }

    public static class Novosibirsk extends Addresses {

        Novosibirsk(String address) {super(address); }

        public static String defaultAddress() { return addressString = "Новосибирск, ул Северная, д 11"; }
    }

    public static class Chelyabinsk extends Addresses {

        Chelyabinsk(String address) {super(address); }

        public static String defaultAddress() { return addressString = "Челябинск, ул Ленина, д 10"; }
    }

    public static class Tyumen extends Addresses {

        Tyumen(String address) {super(address); }

        public static String defaultAddress() { return addressString = "Тюмень, ул Абалакская, д 12"; }
    }

    public static class Perm extends Addresses {

        Perm(String address) {super(address); }

        public static String defaultAddress() { return addressString = "Пермь, ул Абаканская, д 10"; }
    }
}

