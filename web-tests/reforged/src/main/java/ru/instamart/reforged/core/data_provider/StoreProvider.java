package ru.instamart.reforged.core.data_provider;

import org.testng.annotations.DataProvider;
import ru.instamart.reforged.core.enums.ShopUrl;

public class StoreProvider {

    @DataProvider(name = "metro")
    public static Object[][] metro() {
        return new Object[][]{{1, ShopUrl.METRO}};
    }

    @DataProvider(name = "auchan")
    public static Object[][] auchan() {
        return new Object[][]{{72, ShopUrl.AUCHAN}};
    }

    @DataProvider(name = "lenta")
    public static Object[][] lenta() {
        return new Object[][]{{58, ShopUrl.LENTA}};
    }

    @DataProvider(name = "azbukavkusa")
    public static Object[][] azbukavkusa() {
        return new Object[][]{{99, ShopUrl.AZBUKAVKUSA}};
    }

    @DataProvider(name = "vkusvill")
    public static Object[][] vkusvill() {
        return new Object[][]{{23, ShopUrl.VKUSVILL}};
    }
}