package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;

import static ru.instamart.kraken.testdata.BonusPrograms.aeroflot;
import static ru.instamart.kraken.testdata.BonusPrograms.mnogoru;

public final class BonusProvider {

    @DataProvider(name = "bonus")
    public static Object[][] getBonusName() {
        return new Object[][] {
                {mnogoru()},
                {aeroflot()}
        };
    }
}
