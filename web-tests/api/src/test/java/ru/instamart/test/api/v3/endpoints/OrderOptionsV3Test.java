package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.model.v3.StoreV3;
import ru.instamart.api.request.v3.OrderOptionsV3Request;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Опции заказа")
public final class OrderOptionsV3Test extends RestBase {
    StoreV3 store;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        store = apiV3.getStore("METRO, Щелковская");
    }


    @CaseId(853)
    @Story("Опции доставки")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_goods.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Опции заказа доставки Goods")
    public void putOrderOptionsDeliveryGoods(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
    }

    @CaseId(855)
    @Story("Опции доставки")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_sber_devices.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Опции заказа доставки SberDevices")
    public void putOrderOptionsDeliverySberDevices(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
    }

    @CaseId(854)
    @Story("Опции доставки")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_metro_marketplace.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Опции заказа доставки Metro_Marketplace")
    public void putOrderOptionsDeliveryMetroMarketplace(ApiV3TestData testData){
        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);
    }


    @CaseId(677)
    @Story("Опции самовывоза")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_metro_marketplace.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Опции заказа на самовывоз Metro_Marketplace")
    public void putOrderOptionsPickupFromStoreMetroMarketplace(ApiV3TestData testData) {
        Response response = OrderOptionsV3Request.PickupFromStore.PUT(testData, store.getId());
        checkStatusCode200(response);
    }


}