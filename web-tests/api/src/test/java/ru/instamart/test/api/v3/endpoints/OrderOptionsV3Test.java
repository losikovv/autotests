package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.request.v3.OrderOptionsV3Request;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Опции заказа")
public final class OrderOptionsV3Test extends RestBase {

    @CaseId(853)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "goods",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки Goods")
    public void putOrderOptionsDeliveryGoods(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
    }

    @CaseId(855)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "sber_devices",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки SberDevices")
    public void putOrderOptionsDeliverySberDevices(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
    }

    @CaseId(1898)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "Aliexpress",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки Aliexpress")
    public void putOrderOptionsDeliveryAliexpress(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
    }

    @CaseId(1900)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "Auchan",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки Auchan")
    public void putOrderOptionsDeliveryAuchan(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
    }

    @CaseId(854)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки Metro_Marketplace")
    public void putOrderOptionsDeliveryMetroMarketplace(ApiV3TestData testData){
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);

    }

    @CaseId(677)
    @Story("Опции самовывоза")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа на самовывоз Metro_Marketplace")
    public void putOrderOptionsPickupFromStoreMetroMarketplace(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.PickupFromStore.PUT(testData);
        checkStatusCode200(response);
    }

    @CaseId(1902)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "Auchan",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа на самовывоз Auchan")
    public void putOrderOptionsPickupFromStoreAuchan(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.PickupFromStore.PUT(testData);
        checkStatusCode200(response);
    }

}