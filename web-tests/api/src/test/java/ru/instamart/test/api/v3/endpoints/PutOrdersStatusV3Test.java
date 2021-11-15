package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
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
@Feature("Заказы")
public class PutOrdersStatusV3Test extends RestBase {

    OrderV3 order;

    @Issue("INFRADEV-7831")
    @CaseId(675)
    @Story("Отмена заказа на доставку")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_goods.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(  groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class,
            description = "Отмена заказа доставки Goods")
    public void cancelOrderDelivery(ApiV3TestData testData) {
        order = apiV3.createOrderDelivery(testData);
        Response response = OrderV3Request.Cancel.PUT(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @CaseId(868)
    @Issue("DVR-1547")
    @Story("Отмена заказа на самовывоз")
    @JsonDataProvider(path = "data/json_v3/api_v3_test_data_metro_marketplace.json", type = ApiV3DataProvider.ApiV3TestDataRoot.class)
    @Test(  groups = {"api-instamart-regress"},
            dataProvider = "jsonWithoutParallel",
            dataProviderClass = JsonProvider.class,
            description = "Отмена заказа самовывоза Metro_Marketplace")
    public void cancelOrderPickupFromStore(ApiV3TestData testData) {
        order = apiV3.createOrderPickupFromStore(testData);
        Response response = OrderV3Request.Cancel.PUT(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}
