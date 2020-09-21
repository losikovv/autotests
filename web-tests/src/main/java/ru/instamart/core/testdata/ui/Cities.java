package instamart.core.testdata.ui;

import instamart.ui.common.pagesdata.CityData;

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
