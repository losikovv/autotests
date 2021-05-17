package ru.instamart.core.testdata;

import ru.instamart.core.testdata.pagesdata.RegionData;

public class Regions {

    public static RegionData moscow() {
        return new RegionData(
                "msk",
                "Москва",
                "московский регион доставки"
        );
    }

    public static RegionData saintPetersburg() {
        return new RegionData(
                "spb",
                "Санкт-Петербург",
                "питерский регион доставки"
        );
    }

    public static RegionData ekaterinbrg() {
        return new RegionData(
                "ebr",
                "Екатеринбург",
                "екатеринбургский регион доставки");
    }

    public static RegionData kazan() {
        return new RegionData(
                "kzn",
                "Казань",
                "казанский регион доставки"
        );
    }

    public static RegionData nizhnyNovgorod() {
        return new RegionData(
                "nn",
                "Нижний Новгород",
                "регион доставки Нижнего Новгорода"
        );
    }

    public static RegionData ufa() {
        return new RegionData(
                "ufa",
                "Уфа",
                "уфимский регион доставки"
        );
    }

    public static RegionData krasnodar() {
        return new RegionData(
                "krs",
                "Краснодар",
                "краснодарский регион доставки"
        );
    }

    public static RegionData rostovNaDonu() {
        return new RegionData(
                "rnd",
                "Ростов-на-Дону",
                "ростовский регион доставки"
        );
    }

    public static RegionData samara() {
        return new RegionData(
                "smr",
                "Самара",
                "самарский регион доставки"
        );
    }

    public static RegionData voronezh() {
        return new RegionData(
                "vrn",
                "Воронеж",
                "воронежский регион доставки"
        );
    }
}