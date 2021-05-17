package ru.instamart.core.testdata;

import ru.instamart.ui.data.pagesdata.CityData;

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
