package ru.instamart.ui.module.testdata;

import ru.instamart.ui.common.pagesdata.CityData;

public class Cities {

    public static CityData Moscow() {

        return new CityData(
                "Москва",
                "Москве",
                new Object[][] {
                        {"default","rrr"},
                        {"test",""},
                        {"",""}}
        );
    }
}
