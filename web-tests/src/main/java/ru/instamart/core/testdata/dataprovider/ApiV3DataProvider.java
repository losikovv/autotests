package ru.instamart.core.testdata.dataprovider;

import org.testng.annotations.DataProvider;
import ru.instamart.api.common.RestBase;
import ru.instamart.core.testdata.ApiV3TestData;

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
    @DataProvider(name = "itemIds")
    public static Object[][] getItemIds() {
        Object[][] dataArray= new Object[6][1];
        dataArray[0][0]= new ApiV3TestData("15879",200,"sku продукта","14cd5d341d768bd4926fc9f5ce262094","sber_devices", "15000",10,53453, 10000);
        dataArray [1][0] = ApiV3TestData.builder()
               .itemId("15879")
               .statusCode(200)
               .itemIdName("sku продукта")
               .clientToken("14cd5d341d768bd4926fc9f5ce262094")
               .clientTokenName("sber_devices")
               .shipTotal("15000")
               .itemQuantity(10)
               .itemPrice(53453)
               .itemDiscount(10000).build();
        return dataArray;
    }

}
