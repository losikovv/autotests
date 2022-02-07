package ru.instamart.api.dataprovider;

import lombok.Data;
import org.testng.annotations.DataProvider;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.enums.v3.NotificationTypeV3;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.kraken.data_provider.DataList;

import java.util.List;

public class ApiV3DataProvider extends RestBase {

 /*   @DataProvider(name = "itemIds")
    public static Object[][] getItemIds() {
        Object[][] idsArray = new Object[6][9];
        idsArray[0]= new Object[] {"15879",200,"sku продукта","14cd5d341d768bd4926fc9f5ce262094","sber_devices", "15000",10,53453, 10000 };
        idsArray[1]= new Object[] {"23022",422,"sku ритейлера", "14cd5d341d768bd4926fc9f5ce262094","sber_devices", "15000",10,53453, 10000};
        idsArray[2]= new Object[] {"15879",200,"sku продукта","6fae9cd2b268be84e2ab394b6fd0d599","goods" , "15000",10,53453, 10000};
        idsArray[3]= new Object[] {"23022",422,"sku ритейлера", "6fae9cd2b268be84e2ab394b6fd0d599","goods", "15000",10,53453, 10000};
        idsArray[4]= new Object[] {"15879",422,"sku продукта","8055cfd11c887f2887dcd109e66dd166","metro_marketplace" , "15000",10,53453, 10000};
        idsArray[5]= new Object[] {"23022",200,"sku ритейлера", "8055cfd11c887f2887dcd109e66dd166","metro_marketplace", "15000",10,53453, 10000};
        return idsArray;
    }

*/

    @Data
    public static class ApiV3TestDataRoot implements DataList<ApiV3TestData> {
        private List<ApiV3TestData> data;
    }

    @DataProvider(name = "notificationTypes")
    public static Object[][] getNotificationTypes() {
        return new Object[][]{
                {NotificationTypeV3.IN_WORK},
                {NotificationTypeV3.ASSEMBLED},
                {NotificationTypeV3.READY_FOR_DELIVERY},
                {NotificationTypeV3.DELIVERED},
                {NotificationTypeV3.CANCELED}
        };
    }

    @DataProvider(name = "ordersWithDifferentPricers")
    public static Object[][] getOrdersWithDifferentPricers() {
        SessionFactory.makeSession(SessionType.API_V2, SessionProvider.PHONE);
        return new Object[][]{
                {apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_ITEM)},
                //{apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_KILO)},
                {apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACK)},
                {apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACKAGE)}
        };
    }
}
