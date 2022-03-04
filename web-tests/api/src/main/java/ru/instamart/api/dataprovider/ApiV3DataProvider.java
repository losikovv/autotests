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

import static ru.instamart.api.helper.ApiV3Helper.getApiClientToken;
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
                {apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_KILO)},
                {apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACK)},
                {apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 58, ProductPriceTypeV2.PER_PACKAGE)}
        };
    }

    @DataProvider(name = "goods")
    public static Object[][] getItemIdsGoods() {
        String token = getApiClientToken("goods");
        OfferResponse pricerPerItem = getPricerPerItemProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerItem = pricerPerItem.getOffer().getProductSku();

        OfferResponse pricerPerKilo = getPricerPerKiloProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerKilo = pricerPerKilo.getOffer().getProductSku();

        OfferResponse pricerPerPackage = getPricerPerPackageProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerPackage = pricerPerPackage.getOffer().getProductSku();

        OfferResponse pricerPerPack = getPricerPerPackProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerPack = pricerPerPack.getOffer().getProductSku();

        Object[][] idsArray = new Object[4][1];
        idsArray[0] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerItem).shipTotal("1299").itemIdName("PerItem").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("Goods").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[1] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerKilo).shipTotal("1299").itemIdName("PerKilo").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("Goods").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[2] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPackage).shipTotal("1299").itemIdName("PerPackage").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("Goods").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[3] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPack).shipTotal("1299").itemIdName("PerPack").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("Goods").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        return idsArray;

    }

    @DataProvider(name = "sber_devices")
    public static Object[][] getItemIdsSberDevices() {
        String token = getApiClientToken("sber_devices");
        OfferResponse pricerPerItem = getPricerPerItemProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerItem = pricerPerItem.getOffer().getProductSku();

        OfferResponse pricerPerKilo = getPricerPerKiloProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerKilo = pricerPerKilo.getOffer().getProductSku();

        OfferResponse pricerPerPackage = getPricerPerPackageProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerPackage = pricerPerPackage.getOffer().getProductSku();

        OfferResponse pricerPerPack = getPricerPerPackProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerPack = pricerPerPack.getOffer().getProductSku();

        Object[][] idsArray = new Object[4][1];
        idsArray[0] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerItem).shipTotal("1299").itemIdName("PerItem").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("SberDevices").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[1] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerKilo).shipTotal("1299").itemIdName("PerKilo").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("SberDevices").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[2] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPackage).shipTotal("1299").itemIdName("PerPackage").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("SberDevices").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[3] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPack).shipTotal("1299").itemIdName("PerPack").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("SberDevices").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        return idsArray;

    }

    @DataProvider(name = "metro_marketplace")
    public static Object[][] getItemIdsMetroMarketplace() {
        String token = getApiClientToken("metro_marketplace");
        OfferResponse pricerPerItem = getPricerPerItemProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerItem = pricerPerItem.getOffer().getRetailerSku();

        OfferResponse pricerPerKilo = getPricerPerKiloProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerKilo = pricerPerKilo.getOffer().getRetailerSku();

        OfferResponse pricerPerPackage = getPricerPerPackageProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerPackage = pricerPerPackage.getOffer().getRetailerSku();

        OfferResponse pricerPerPack = getPricerPerPackProductFilter(1, 8, 2, OfferResponse.class);
        String retailerSkuPerPack = pricerPerPack.getOffer().getRetailerSku();


        Object[][] idsArray = new Object[4][1];
        idsArray[0] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerItem).itemIdName("PerItem").shipTotal("1299").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("MetroMarketplace").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[1] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerKilo).itemIdName("PerKilo").shipTotal("1299").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("MetroMarketplace").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[2] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPackage).itemIdName("PerPackage").shipTotal("1299").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("MetroMarketplace").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        idsArray[3] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPack).itemIdName("PerPack").shipTotal("1299").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("metro").clientTokenName("MetroMarketplace").storeId("d1106342-817f-4c3e-8c18-0005295f641a").build()};
        return idsArray;
    }

    @DataProvider(name = "Aliexpress")
    public static Object[][] getItemIdsAliexpress() {
        String token = getApiClientToken("aliexpress");
        OfferResponse pricerPerItem = getPricerPerItemProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerItem = pricerPerItem.getOffer().getRetailerSku();

        OfferResponse pricerPerKilo = getPricerPerKiloProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerKilo = pricerPerKilo.getOffer().getRetailerSku();

        OfferResponse pricerPerPackage = getPricerPerPackageProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerPackage = pricerPerPackage.getOffer().getRetailerSku();

        OfferResponse pricerPerPack = getPricerPerPackProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerPack = pricerPerPack.getOffer().getRetailerSku();

        Object[][] idsArray = new Object[4][1];
        idsArray[0] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerItem).shipTotal("1299").itemIdName("PerItem").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Aliexpress").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};
        idsArray[1] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerKilo).shipTotal("1299").itemIdName("PerKilo").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Aliexpress").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};
        idsArray[2] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPackage).shipTotal("1299").itemIdName("PerPackage").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Aliexpress").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};
        idsArray[3] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPack).shipTotal("1299").itemIdName("PerPack").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Aliexpress").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};
        return idsArray;

    }

    @DataProvider(name = "Auchan")
    public static Object[][] getItemIdsAuchan() {
        String token = getApiClientToken("auchan");
        OfferResponse pricerPerItem = getPricerPerItemProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerItem = pricerPerItem.getOffer().getRetailerSku();

        OfferResponse pricerPerKilo = getPricerPerKiloProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerKilo = pricerPerKilo.getOffer().getRetailerSku();

        OfferResponse pricerPerPackage = getPricerPerPackageProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerPackage = pricerPerPackage.getOffer().getRetailerSku();

        OfferResponse pricerPerPack = getPricerPerPackProductFilter(15, 72, 2, OfferResponse.class);
        String retailerSkuPerPack = pricerPerPack.getOffer().getRetailerSku();

        Object[][] idsArray = new Object[4][1];

        idsArray[0] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerItem).shipTotal("1299").itemIdName("PerItem").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Auchan").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};
        idsArray[1] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerKilo).shipTotal("1299").itemIdName("PerKilo").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Auchan").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};
        idsArray[2] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPackage).shipTotal("1299").itemIdName("PerPackage").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Auchan").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};
        idsArray[3] = new Object[]{ApiV3TestData.builder().itemId(retailerSkuPerPack).shipTotal("1299").itemIdName("PerPack").itemQuantity(5).itemPrice(1000).itemDiscount(100).itemPromoTotal(100).clientToken(token).retailer_id("auchan").clientTokenName("Auchan").storeId("4872ead0-274b-49a2-955e-a5101a7de9cb").build()};

        return idsArray;

    }
}