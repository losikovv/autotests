package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;
import ru.instamart.api.common.RestAddresses;

public final class CityProvider {

    @DataProvider(name = "city")
    public static Object[][] getCity() {
        return new Object[][] {
                {RestAddresses.Moscow.defaultAddress()},
                {RestAddresses.SaintPetersburg.defaultAddress()},
                {RestAddresses.Kazan.defaultAddress()},
                {RestAddresses.Ekaterinburg.defaultAddress()},
                {RestAddresses.NizhnyNovgorod.defaultAddress()},
                {RestAddresses.RostovNaDonu.defaultAddress()},
                {RestAddresses.Ufa.defaultAddress()},
                {RestAddresses.Krasnodar.defaultAddress()},
                {RestAddresses.Samara.defaultAddress()},
                {RestAddresses.Voronezh.defaultAddress()},
                {RestAddresses.Omsk.defaultAddress()},
                {RestAddresses.Volgograd.defaultAddress()},
                {RestAddresses.Novosibirsk.defaultAddress()},
                {RestAddresses.Chelyabinsk.defaultAddress()},
                {RestAddresses.Tyumen.defaultAddress()},
                {RestAddresses.Perm.defaultAddress()}
        };
    }
}
