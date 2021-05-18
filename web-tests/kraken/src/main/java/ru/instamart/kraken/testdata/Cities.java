package ru.instamart.kraken.testdata;

import ru.instamart.kraken.testdata.pagesdata.CityData;

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
