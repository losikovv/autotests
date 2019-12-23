package ru.instamart.application.lib;

import ru.instamart.application.models.CityData;

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
