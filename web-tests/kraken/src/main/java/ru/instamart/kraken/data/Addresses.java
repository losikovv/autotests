package ru.instamart.kraken.data;

public final class Addresses {
    
    public static class Moscow {

        public static String defaultAddress() {
            return "проспект Мира, 211к1";
        }

        public static String testAddress() {
            return "Ленинский проспект, 67, Москва";
        }

        public static String outOfZoneAddress() {
            return "село Каракулино, 1";
        }

        public static String outOfZoneAddressMoscow() {
            return "Подольск, ул Дружбы, д 15";
        }
    }

    public static class SaintPetersburg {

        public static String defaultAddress() {
            return "Санкт-Петербург, Литейный пр-кт, д 10";
        }
    }

    public static class Kazan {

        public static String defaultAddress() {
            return "Казань, ул Мулланура Вахитова, д 10";
        }
    }

    public static class Ekaterinburg {

        public static String defaultAddress() {
            return "Екатеринбург, ул Советская, д 9";
        }
    }

    public static class NizhnyNovgorod {

        public static String defaultAddress() {
            return "Нижний Новгород, тер Гск Кузнечиха, д 1";
        }
    }

    public static class RostovNaDonu {

        public static String defaultAddress() {
            return "Ростов-на-Дону, ул 2-й Пятилетки, д 23";
        }
    }

    public static class Ufa {

        public static String defaultAddress() {
            return "Уфа, ул Кирова, д 1";
        }
    }

    public static class Krasnodar {

        public static String defaultAddress() {
            return "Краснодар, ул Аграрная, д 1";
        }
    }

    public static class Samara {

        public static String defaultAddress() {
            return "Самара, ул Ленинская, д 10";
        }
    }

    public static class Voronezh {

        public static String defaultAddress() {
            return "Воронеж, ул Ленина, д 12";
        }
    }

    public static class Omsk {

        public static String defaultAddress() {
            return "Омск, ул Омская, д 110";
        }
    }

    public static class Volgograd {

        public static String defaultAddress() {
            return "Волгоград, ул Абаканская, д 11";
        }
    }

    public static class Novosibirsk {

        public static String defaultAddress() {
            return "Новосибирск, ул Северная, д 11";
        }
    }

    public static class Chelyabinsk {

        public static String defaultAddress() {
            return "Челябинск, ул Ленина, д 10";
        }
    }

    public static class Tyumen {

        public static String defaultAddress() {
            return "Тюмень, ул Абалакская, д 12";
        }
    }

    public static class Perm {

        public static String defaultAddress() {
            return "Пермь, ул Абаканская, д 10";
        }
    }
}

