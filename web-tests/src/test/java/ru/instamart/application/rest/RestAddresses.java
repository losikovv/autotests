package ru.instamart.application.rest;

import ru.instamart.application.rest.objects.Address;

public class RestAddresses {

    public static class Moscow {

        public static Address learningCenter() {
            return new Address(
                    "город Мытищи",
                    "Центральная ул.",
                    "5А",
                    55.9032988,
                    37.6291675);
        }

        // 03.12.19 по этому адресу доступны 4 ритейлера
        public static Address defaultAddress() {
            return new Address(
                    "Москва",
                    "просп. Мира",
                    "211, стр. 1",
                    55.844041,
                    37.66265);
        }
    }

    public static class SaintPetersburg {

        public static Address defaultAddress() {
            return new Address(
                    "Санкт-Петербург",
                    "Литейный пр.",
                    "10",
                    59.9460157,
                    30.3490454);
        }
    }

    public static class Kazan {

        public static Address defaultAddress() {
            return new Address(
                    "г. Казань",
                    "ул. Мулланура Вахитова",
                    "11 строение 2",
                    55.8132214,
                    49.0874435);
        }
    }

    public static class Ekaterinburg {

        public static Address defaultAddress() {
            return new Address(
                    "г. Екатеринбург",
                    "ул. Советская",
                    "9",
                    56.851999,
                    60.628987);
        }
    }

    public static class NizhnyNovgorod {

        public static Address defaultAddress() {
            return new Address(
                    "г. Нижний Новгород",
                    "ул. Кащенко",
                    "81",
                    56.2312899,
                    43.979083);
        }
    }

    public static class RostovNaDonu {

        public static Address defaultAddress() {
            return new Address(
                    "г. Ростов-на-Дону",
                    "ул. 2-й Пятилетки",
                    "23",
                    47.2625246,
                    39.6913188);
        }
    }

    public static class Ufa {

        public static Address defaultAddress() {
            return new Address(
                    "Уфимский р-н",
                    "ул. Кирова",
                    "1",
                    54.732043,
                    55.944102);
        }
    }

    public static class Krasnodar {

        public static Address defaultAddress() {
            return new Address(
                    "г. Краснодар",
                    "ул. Аграрная",
                    "15",
                    45.0752396,
                    39.0292711);
        }
    }

    public static class Samara {

        public static Address defaultAddress() {
            return new Address(
                    "г. Самара",
                    "ул. Ленинская",
                    "100",
                    53.187137,
                    50.1058121);
        }
    }

    public static class Voronezh {

        public static Address defaultAddress() {
            return new Address(
                    "г. Воронеж",
                    "ул. Ленина",
                    "12",
                    51.6926144,
                    39.2243927);
        }
    }

    public static class Omsk {

        public static Address defaultAddress() {
            return new Address(
                    "г. Омск",
                    "ул. Омская",
                    "110",
                    54.9902494,
                    73.4040309);
        }
    }

    public static class Volgograd {

        public static Address defaultAddress() {
            return new Address(
                    "г. Волгоград",
                    "ул. Абаканская",
                    "11",
                    48.7129627,
                    44.4350837);
        }
    }

    public static class Novosibirsk {

        public static Address defaultAddress() {
            return new Address(
                    "г. Новосибирск",
                    "ул. Северная",
                    "11",
                    55.070567,
                    82.913387);
        }
    }

    public static class Chelyabinsk {

        public static Address defaultAddress() {
            return new Address(
                    "г. Челябинск",
                    "ул. Ленина",
                    "10",
                    55.1535708,
                    61.3078397);
        }
    }

    public static class Tyumen {

        public static Address defaultAddress() {
            return new Address(
                    "г. Тюмень",
                    "ул Абалакская",
                    "д 12",
                    57.2391352,
                    65.5135712);
        }
    }

    public static class Perm {

        public static Address defaultAddress() {
            return new Address(
                    "г. Пермь",
                    "ул Абаканская",
                    "д 10",
                    58.0466565,
                    56.1072683);
        }
    }
}
