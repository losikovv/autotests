package ru.instamart.core.testdata.dataprovider;

import org.testng.annotations.DataProvider;
import ru.instamart.api.common.RestBase;

public class ApiV3DataProvider extends RestBase {

    @DataProvider(name = "itemIds")
    public static Object[][] getItemIds() {
        Object[][] idsArray = new Object[2][3];
        idsArray[0][0] = "15879";
        idsArray[0][1] = 200;
        idsArray[0][2] = "Валидный айдишник";
        idsArray[1][0] = "123123";
        idsArray[1][1] = 422;
        idsArray[1][2] = "Невалидный айдишник";
        return idsArray;
    }
}
