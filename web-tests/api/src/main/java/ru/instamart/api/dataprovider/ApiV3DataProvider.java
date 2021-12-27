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
import ru.instamart.k8s.rails_response.OfferResponse;
import ru.instamart.kraken.data_provider.DataList;

import java.util.List;

import static ru.instamart.api.helper.K8sHelper.*;

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

    @DataProvider(name = "goods")
    public static Object[][] getItemIdsGoods() {
        OfferResponse pricerPerItem = getPricerPerItem(1,8, OfferResponse.class);
        String productSkuPerItem = pricerPerItem.getOffer().getProductSku();

        OfferResponse pricerPerKilo = getPricerPerKilo(1,8, OfferResponse.class);
        String productSkuPerKilo = pricerPerKilo.getOffer().getProductSku();

        OfferResponse pricerPerPackage = getPricerPerPackage(1,8,OfferResponse.class);
        String productSkuPerPackage = pricerPerPackage.getOffer().getRetailerSku();

        OfferResponse pricerPerPack = getPricerPerPack(1,8,OfferResponse.class);
        String productSkuPerPack = pricerPerPack.getOffer().getRetailerSku();

        Object[][] idsArray = new Object[1][4];
        idsArray[0] = new Object[]{ productSkuPerItem, 200, "sku продукта", "6fae9cd2b268be84e2ab394b6fd0d599", "goods", "15000", 10, 53453, 10000};
        idsArray[1] = new Object[]{ productSkuPerKilo, 200, "sku продукта", "6fae9cd2b268be84e2ab394b6fd0d599", "goods", "15000", 10, 53453, 10000};
        idsArray[1] = new Object[]{ productSkuPerPackage, 200, "sku продукта", "6fae9cd2b268be84e2ab394b6fd0d599", "goods", "15000", 10, 53453, 10000};
        idsArray[1] = new Object[]{ productSkuPerPack, 200, "sku продукта", "6fae9cd2b268be84e2ab394b6fd0d599", "goods", "15000", 10, 53453, 10000};
        return idsArray;
    }

    @DataProvider(name = "sber_devices")
    public static Object[][] getItemIdsSberDevices() {
        OfferResponse pricerPerItem = getPricerPerItem(1,8, OfferResponse.class);
        String productSkuPerItem = pricerPerItem.getOffer().getProductSku();

        OfferResponse pricerPerKilo = getPricerPerKilo(1,8, OfferResponse.class);
        String productSkuPerKilo = pricerPerKilo.getOffer().getProductSku();

        OfferResponse pricerPerPackage = getPricerPerPackage(1,8,OfferResponse.class);
        String productSkuPerPackage = pricerPerPackage.getOffer().getRetailerSku();

        OfferResponse pricerPerPack = getPricerPerPack(1,8,OfferResponse.class);
        String productSkuPerPack = pricerPerPack.getOffer().getRetailerSku();

        Object[][] idsArray = new Object[1][4];
        idsArray[0] = new Object[]{ productSkuPerItem, 200, "sku продукта", "14cd5d341d768bd4926fc9f5ce262094", "sber_devices", "15000", 10, 53453, 10000};
        idsArray[1] = new Object[]{ productSkuPerKilo, 200, "sku продукта", "14cd5d341d768bd4926fc9f5ce262094", "sber_devices", "15000", 10, 53453, 10000};
        idsArray[1] = new Object[]{ productSkuPerPackage, 200, "sku продукта", "14cd5d341d768bd4926fc9f5ce262094", "sber_devices", "15000", 10, 53453, 10000};
        idsArray[1] = new Object[]{ productSkuPerPack, 200, "sku продукта", "14cd5d341d768bd4926fc9f5ce262094", "sber_devices", "15000", 10, 53453, 10000};
        return idsArray;
    }
    @DataProvider(name = "metro_marketplace")
    public static Object[][] getItemIdsMetroMarketplace() {
        OfferResponse pricerPerItem = getPricerPerItem(1,8, OfferResponse.class);
        String retailerSkuPerItem = pricerPerItem.getOffer().getRetailerSku();

        OfferResponse pricerPerKilo = getPricerPerKilo(1,8, OfferResponse.class);
        String retailerSkuPerKilo = pricerPerKilo.getOffer().getRetailerSku();

        OfferResponse pricerPerPackage = getPricerPerPackage(1,8,OfferResponse.class);
        String retailerSkuPerPackage = pricerPerPackage.getOffer().getRetailerSku();

        OfferResponse pricerPerPack = getPricerPerPack(1,8,OfferResponse.class);
        String retailerSkuPerPack = pricerPerPack.getOffer().getRetailerSku();

        Object[][] idsArray = new Object[1][4];
        idsArray[0] = new Object[]{ retailerSkuPerItem, 200, "sku ритейлера", "8055cfd11c887f2887dcd109e66dd166", "metro_marketplace", "15000", 10, 53453, 10000};
        idsArray[1] = new Object[]{ retailerSkuPerKilo, 200, "sku ритейлера", "8055cfd11c887f2887dcd109e66dd166", "metro_marketplace", "15000", 10, 53453, 10000};
        idsArray[2] = new Object[]{ retailerSkuPerPackage, 200, "sku ритейлера", "8055cfd11c887f2887dcd109e66dd166", "metro_marketplace", "15000", 10, 53453, 10000};
        idsArray[3] = new Object[]{ retailerSkuPerPack, 200, "sku ритейлера", "8055cfd11c887f2887dcd109e66dd166", "metro_marketplace", "15000", 10, 53453, 10000};
        return idsArray;
    }
}