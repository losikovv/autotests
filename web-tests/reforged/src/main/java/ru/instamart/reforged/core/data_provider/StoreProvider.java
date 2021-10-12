package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;
import ru.instamart.reforged.core.enums.ShopUrl;

public class StoreProvider {

    @DataProvider(name = "storeData")
    public static Object[][] getStoreData() {
        return new Object[][]{
                {1, ShopUrl.METRO},
                {72, ShopUrl.AUCHAN},
                {58, ShopUrl.LENTA},
                {99, ShopUrl.AZBUKAVKUSA},
                {23, ShopUrl.VKUSVILL}
        };
    }
}