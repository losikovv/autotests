package ru.instamart.ui.module.testdata;

import ru.instamart.ui.common.pagesdata.LoyaltiesData;

public class BonusPrograms {

    public static LoyaltiesData mnogoru() {
        return new LoyaltiesData(
                "Много.Ру",
                "11600350",
                "бонусная карта Много.Ру"
        );
    }

    public static LoyaltiesData aeroflot() {
        return new LoyaltiesData(
                "«Аэрофлот Бонус»",
                "71891831",
                "карта Аэрофлот Бонус"
        );
    }
}
