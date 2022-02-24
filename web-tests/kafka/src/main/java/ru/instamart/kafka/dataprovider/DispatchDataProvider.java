package ru.instamart.kafka.dataprovider;

import org.testng.annotations.DataProvider;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;


public class DispatchDataProvider {

    @DataProvider(name = "shopperUniversal")
    public static Object[][] shopperUniversal(){
        UserData[] users1 = {UserManager.getShp6Shopper1()};
        UserData[] users2 = {UserManager.getShp6Shopper1(), UserManager.getShp6Shopper2()};
        return new Object[][]{
                {users1},
                {users2}
        };
    }
}
