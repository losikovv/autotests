package ru.instamart.api.common;

import ru.instamart.api.model.v2.AddressV2;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;

import static java.util.List.of;

public class RestAddresses {

    public static List<AddressV2> getDefaultAllAddress() {
        return of(Moscow.defaultAddress(), SaintPetersburg.defaultAddress(), Kazan.defaultAddress(),
                Ekaterinburg.defaultAddress(), NizhnyNovgorod.defaultAddress(), RostovNaDonu.defaultAddress(),
                Ufa.defaultAddress(), Krasnodar.defaultAddress(), Samara.defaultAddress(),
                Voronezh.defaultAddress(), Omsk.defaultAddress(), Volgograd.defaultAddress(),
                Novosibirsk.defaultAddress(), Chelyabinsk.defaultAddress(), Tyumen.defaultAddress(),
                Perm.defaultAddress());
    }

    public static AddressV2 getDefaultAddress() {
        return (EnvironmentProperties.Env.isProduction() ? Moscow.defaultProdAddress() : Moscow.defaultAddress());
    }

    public static class Moscow {

        public static AddressV2 learningCenter() {
            return AddressV2.builder()
                    .city("город Мытищи")
                    .street("Центральная ул.")
                    .building("5А")
                    .lat(55.9032988)
                    .lon(37.6291675)
                    .build();
        }

        // 03.12.19 по этому адресу доступны 4 ритейлера
        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("Москва")
                    .street("просп. Мира")
                    .building("211, стр. 1")
                    .lat(55.844041)
                    .lon(37.66265)
                    .build();
        }

        public static AddressV2 defaultProdAddress() {
            return AddressV2.builder()
                    .city("посёлок Нагорное")
                    .street("Центральная улица")
                    .building("5А")
                    .lat(55.903421) //55.90342135575542
                    .lon(37.629161) //37.62916119842928
                    .build();
        }

        //По этому адресу работает только 1-я зона доставки Metro с пересекающимися зонами (sid = 8)
        public static AddressV2 metroCrossZone1Address() {
            return AddressV2.builder()
                    .city("Москва")
                    .street("улица Первомайская")
                    .building("3")
                    .lat(55.790980698484084)
                    .lon(37.772179471321564)
                    .build();
        }

        //По этому адресу работает только 2-я зона доставки Metro с пересекающимися зонами (sid = 8)
        public static AddressV2 metroCrossZone2Address() {
            return AddressV2.builder()
                    .city("Москва")
                    .street("улица Большая Косинская")
                    .building("118")
                    .lat(55.723811890093366)
                    .lon(37.85521172714293)
                    .build();
        }

        //Адрес, используемый в кейсах предзамен
        public static AddressV2 prereplacementAddress() {
            return AddressV2.builder()
                    .city("Москва")
                    .street("улица Новозаводская")
                    .building("14А")
                    .lat(55.75385309349555)
                    .lon(37.49936848919324)
                    .build();
        }

        public static class Vkusvill {

            public static AddressV2 michurinsky() {
                return AddressV2.builder()
                        .city("Москва")
                        .street("Мичуринский пр")
                        .building("16")
                        .lat(55.703119)
                        .lon(37.506047)
                        .build();
            }
        }
    }

    public static class SaintPetersburg {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("Санкт-Петербург")
                    .street("Литейный пр.")
                    .building("10")
                    .lat(59.9460157)
                    .lon(30.3490454)
                    .build();
        }
    }

    public static class Kazan {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Казань")
                    .street("ул. Мулланура Вахитова")
                    .building("11 строение 2")
                    .lat(55.8132214)
                    .lon(49.0874435)
                    .build();
        }
    }

    public static class Ekaterinburg {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Екатеринбург")
                    .street("ул. Советская")
                    .building("9")
                    .lat(56.851999)
                    .lon(60.628987)
                    .build();
        }
    }

    public static class NizhnyNovgorod {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Нижний Новгород")
                    .street("ул. Кащенко")
                    .building("81")
                    .lat(56.2312899)
                    .lon(43.979083)
                    .build();
        }
    }

    public static class RostovNaDonu {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Ростов-на-Дону")
                    .street("ул. 2-й Пятилетки")
                    .building("23")
                    .lat(47.2625246)
                    .lon(39.6913188)
                    .build();
        }
    }

    public static class Ufa {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("Уфимский р-н")
                    .street("ул. Кирова")
                    .building("1")
                    .lat(54.732043)
                    .lon(55.944102)
                    .build();
        }
    }

    public static class Krasnodar {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Краснодар")
                    .street("ул. Аграрная")
                    .building("15")
                    .lat(45.0752396)
                    .lon(39.0292711)
                    .build();
        }

        public static AddressV2 outOfDeliveryArea() {
            return AddressV2.builder()
                    .city("г. Хадыженск")
                    .street("ул. Грибоедова")
                    .building("42")
                    .lat(44.4096801)
                    .lon(39.5278277)
                    .build();
        }
    }

    public static class Samara {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Самара")
                    .street("ул. Ленинская")
                    .building("100")
                    .lat(53.187137)
                    .lon(50.1058121)
                    .build();
        }
    }

    public static class Voronezh {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Воронеж")
                    .street("ул. Ленина")
                    .building("12")
                    .lat(51.6926144)
                    .lon(39.2243927)
                    .build();
        }
    }

    public static class Omsk {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Омск")
                    .street("ул. Омская")
                    .building("110")
                    .lat(54.9902494)
                    .lon(73.4040309)
                    .build();
        }
    }

    public static class Volgograd {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Волгоград")
                    .street("ул. Абаканская")
                    .building("11")
                    .lat(48.7129627)
                    .lon(44.4350837)
                    .build();
        }
    }

    public static class Novosibirsk {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Новосибирск")
                    .street("ул. Северная")
                    .building("11")
                    .lat(55.070567)
                    .lon(82.913387)
                    .build();
        }
    }

    public static class Chelyabinsk {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Челябинск")
                    .street("ул. Ленина")
                    .building("10")
                    .lat(55.1535708)
                    .lon(61.3078397)
                    .build();
        }
    }

    public static class Tyumen {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Тюмень")
                    .street("ул Абалакская")
                    .building("д 12")
                    .lat(57.2391352)
                    .lon(65.5135712)
                    .build();
        }
    }

    public static class Perm {

        public static AddressV2 defaultAddress() {
            return AddressV2.builder()
                    .city("г. Пермь")
                    .street("ул Абаканская")
                    .building("д 10")
                    .lat(58.0466565)
                    .lon(56.1072683)
                    .build();
        }
    }
}
