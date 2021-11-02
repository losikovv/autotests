package ru.instamart.api.dataprovider;

import org.testng.annotations.DataProvider;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.testdata.ApiV3TestData;

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


    @DataProvider(name = "sber_devices")
    public static Object[][] getItemIdsSberDiveces() {
        Object[][] dataArray= new Object[6][1];
        //dataArray[0][0]= new ApiV3TestData("15879",200,"sku продукта","14cd5d341d768bd4926fc9f5ce262094","sber_devices", "15000",10,53453, 10000,1);
        dataArray [0][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("sber_devices")
                .clientToken("14cd5d341d768bd4926fc9f5ce262094")
                .itemId("15879")
                .itemIdName("sku продукта Шоколадные конфеты Merci ассорти 400 г PerItem - поштучный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [1][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("sber_devices")
                .clientToken("14cd5d341d768bd4926fc9f5ce262094")
                .itemId("30952")
                .itemIdName("sku продукта Уксус Kuhne яблочный 5% PerItem - поштучный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [2][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("sber_devices")
                .clientToken("14cd5d341d768bd4926fc9f5ce262094")
                .itemId("10712")
                .itemIdName("sku продукта Бананы PerKilo - весовой")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [3][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("sber_devices")
                .clientToken("14cd5d341d768bd4926fc9f5ce262094")
                .itemId("45576")
                .itemIdName("sku продукта Абрикосы Россия ~1 кг PerPackage - весовой упакованный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [4][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("sber_devices")
                .clientToken("14cd5d341d768bd4926fc9f5ce262094")
                .itemId("128861")
                .itemIdName("sku продукта Тушка Qegg цыпленка корнишон замороженная ~700 г PerPackage - весовой упакованный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [5][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("sber_devices")
                .clientToken("14cd5d341d768bd4926fc9f5ce262094")
                .itemId("12156")
                .itemIdName("sku продукта Газированный напиток Fanta апельсин 0,33 PerPack - упаковочный (ящик, коробка)")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        return dataArray;
    }
    @DataProvider(name = "goods")
    public static Object[][] getItemIdsGoods() {
        Object[][] dataArray= new Object[6][1];
        //dataArray[0][0]= new ApiV3TestData("15879",200,"sku продукта","14cd5d341d768bd4926fc9f5ce262094","sber_devices", "15000",10,53453, 10000,1);
        dataArray [0][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("goods")
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .itemId("15879")
                .itemIdName("sku продукта Шоколадные конфеты Merci ассорти 400 г PerItem - поштучный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [1][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("goods")
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .itemId("30952")
                .itemIdName("sku продукта Уксус Kuhne яблочный 5% PerItem - поштучный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [2][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("goods")
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .itemId("10712")
                .itemIdName("sku продукта Бананы PerKilo - весовой")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [3][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("goods")
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .itemId("45576")
                .itemIdName("sku продукта Абрикосы Россия ~1 кг PerPackage - весовой упакованный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [4][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("goods")
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .itemId("128861")
                .itemIdName("sku продукта Тушка Qegg цыпленка корнишон замороженная ~700 г PerPackage - весовой упакованный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [5][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("goods")
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .itemId("12156")
                .itemIdName("sku продукта Газированный напиток Fanta апельсин 0,33 PerPack - упаковочный (ящик, коробка)")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        return dataArray;
    }
    @DataProvider(name = "metro_marketplace")
    public static Object[][] getItemIdsMetroMarketplace() {
        Object[][] dataArray= new Object[6][1];
        //dataArray[0][0]= new ApiV3TestData("15879",200,"sku продукта","14cd5d341d768bd4926fc9f5ce262094","sber_devices", "15000",10,53453, 10000,1);
        dataArray [0][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("metro_marketplace")
                .clientToken("8055cfd11c887f2887dcd109e66dd166")
                .itemId("23020")
                .itemIdName("sku ритейлера Шоколадные конфеты Merci ассорти 400 г PerItem - поштучный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [1][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("metro_marketplace")
                .clientToken("8055cfd11c887f2887dcd109e66dd166")
                .itemId("110581")
                .itemIdName("sku ритейлера Уксус Kuhne яблочный 5% PerItem - поштучный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [2][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("metro_marketplace")
                .clientToken("8055cfd11c887f2887dcd109e66dd166")
                .itemId("557198")
                .itemIdName("sku ритейлера Бананы PerKilo - весовой")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [3][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("metro_marketplace")
                .clientToken("8055cfd11c887f2887dcd109e66dd166")
                .itemId("567196")
                .itemIdName("sku ритейлера Абрикосы Россия ~1 кг PerPackage - весовой упакованный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [4][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("metro_marketplace")
                .clientToken("8055cfd11c887f2887dcd109e66dd166")
                .itemId("588842")
                .itemIdName("sku ритейлера Тушка Qegg цыпленка корнишон замороженная ~700 г PerPackage - весовой упакованный")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        dataArray [5][0] = ApiV3TestData.builder()
                .statusCode(200)
                .clientTokenName("metro_marketplace")
                .clientToken("8055cfd11c887f2887dcd109e66dd166")
                .itemId("51505")
                .itemIdName("sku ритейлера Газированный напиток Fanta апельсин 0,33 PerPack - упаковочный (ящик, коробка)")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .shipTotal("15000").build();
        return dataArray;
    }
}
