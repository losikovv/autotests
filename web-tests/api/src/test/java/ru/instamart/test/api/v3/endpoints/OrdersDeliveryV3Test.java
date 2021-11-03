package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.model.v3.OrderV3;
import ru.instamart.api.request.v3.OrderV3Request;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Создание заказа")

public class OrdersDeliveryV3Test extends RestBase {

    @CaseId(858)
    @Story("Доставка")
    @Issue("STF-9456")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_goods.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class,
            description = "Создание заказа на доставку Goods")
    public void postOrderDeliveryGoods(ApiV3TestData testData) {

        OrderV3 orderGoods = apiV3.createOrderDelivery(testData);

        response = OrderV3Request.GET(orderGoods.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderGoods.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @CaseId(860)
    @Story("Доставка")
    @Issue("STF-9456")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_sber_devices.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class,
            description = "Создание на доставку заказа Sber_devices")
    public void postOrderDeliverySberDevices(ApiV3TestData testData) {

        OrderV3 orderSberDevices = apiV3.createOrderDelivery(testData);

        response = OrderV3Request.GET(orderSberDevices.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderSberDevices.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }


    @CaseId(859)
    @Story("Доставка")
    @Issue("STF-9456")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_metro_marketplace.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class,
            description = "Создание заказа на доставку Metro_Marketplace")
    public void postOrderDeliveryMetroMarketplace(ApiV3TestData testData) {

        OrderV3 orderMetroMarketplace = apiV3.createOrderDelivery(testData);

        response = OrderV3Request.GET(orderMetroMarketplace.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderMetroMarketplace.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}


