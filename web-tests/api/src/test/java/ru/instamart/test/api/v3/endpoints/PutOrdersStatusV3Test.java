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

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Отмена заказа")

public class PutOrdersStatusV3Test extends RestBase {

    OrderV3 order;

   /*@BeforeMethod(alwaysRun = true)
    public void createOrder() {
        ApiV3TestData testData = ApiV3TestData
                .builder()
                .shipTotal("15000")
                .itemId("15879")
                .itemQuantity(1)
                .itemPrice(10000)
                .itemDiscount(1000)
                .itemPromoTotal(1000)
                .clientToken("6fae9cd2b268be84e2ab394b6fd0d599")
                .build();

        order = apiV3.createOrderDelivery(testData);
    }*/

    @Issue("INFRADEV-7831")
    @CaseId(675)
    @Story("Отмена заказа на доставку")
    @Test(groups = {"api-instamart-smoke"},
            dataProvider = "goods",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Отмена заказа доставки Goods")
    public void cancelOrderDelivery(ApiV3TestData testData) {
        order = apiV3.createOrderDelivery(testData);
        Response response = OrderV3Request.Cancel.PUT(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @CaseId(868)
    @Issue("DVR-1547")
    @Story("Отмена заказа на самовывоз")
    @Test(groups = {"api-instamart-smoke"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Отмена заказа самовывоза Metro_Marketplace")
    public void cancelOrderPickupFromStore(ApiV3TestData testData) {
        order = apiV3.createOrderPickupFromStore(testData);
        Response response = OrderV3Request.Cancel.PUT(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}
