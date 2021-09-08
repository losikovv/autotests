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

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Показать опции заказа")

public class OrderOptionsV3Test extends RestBase {

    //OrderV3 order;
    @CaseId(853)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "goods",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки Goods" )
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

    @CaseId(854)
    @Story("Опции доставки")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки Metro_Marketplace" )
    public void putOrderOptionsDeliveryMetroMarketplace(ApiV3TestData testData) {

        Response response = OrderOptionsV3Request.Delivery.PUT(testData);
        checkStatusCode200(response);

    }

    StoreV3 store;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        store = apiV3.getStore("METRO, Щелковская");
    }

    @CaseId(677)
    @Story("Опции самовывоза")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа на самовывоз Metro_Marketplace" )
    public void putOrderOptionsPickupFromStoreMetroMarketplace(ApiV3TestData testData) {

        Response response = OrderOptionsV3Request.PickupFromStore.PUT(testData, store.getId());
        checkStatusCode200(response);

    }



}