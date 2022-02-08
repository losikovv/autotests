package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.model.v3.OrderV3;
import ru.instamart.api.request.v3.OrderV3Request;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Заказы")
public class GetOrderV3Test extends RestBase {

    OrderV3 order;

    @CaseId(862)
    @Story("Заказ на доставку")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "goods",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Опции заказа доставки Goods")
    public void getOrder(ApiV3TestData testData) {
        order = apiV3.createOrderDelivery(testData);
        Response response = OrderV3Request.GET(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @CaseId(863)
    @Story("Заказ на самовывоз")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Показать заказ по uuid самовывоз Metro_Marketplace")

    public void getOrders(ApiV3TestData testData) {
        order = apiV3.createOrderPickupFromStore(testData);
        Response response = OrderV3Request.GET(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}
