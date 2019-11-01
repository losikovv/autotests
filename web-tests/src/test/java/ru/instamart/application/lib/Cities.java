package ru.instamart.application.lib;

import ru.instamart.application.models.CityData;

import java.util.Arrays;
import java.util.List;

public class Cities {

    public static CityData Moscow() {

        List<String> addresses = Arrays.asList(
                "1",
                "2",
                "3"
        );

        return new CityData("Москва", "Москве", addresses);
    }

}
