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
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Заказы")
public class PutOrdersStatusV3Test extends RestBase {

    OrderV3 order;

    @TmsLink("675")
    @Story("Отмена заказа на доставку")
    @Test(  groups = {API_INSTAMART_REGRESS, "api-v3"},
            dataProvider = "goods",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Отмена заказа доставки Goods")
    public void cancelOrderDelivery(ApiV3TestData testData) {
        order = apiV3.createOrderDelivery(testData);
        Response response = OrderV3Request.Cancel.PUT(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @TmsLink("868")
    @Story("Отмена заказа на самовывоз")
    @Test(  groups = {API_INSTAMART_REGRESS, "api-v3"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Отмена заказа самовывоза Metro_Marketplace")
    public void cancelOrderPickupFromStore(ApiV3TestData testData) {
        order = apiV3.createOrderPickupFromStore(testData);
        Response response = OrderV3Request.Cancel.PUT(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}
