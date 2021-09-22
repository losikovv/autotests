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
@Feature("Показать заказ по uuid")
public class GetOrderV3Test extends RestBase {

    OrderV3 order;

   /* @BeforeMethod(alwaysRun = true)
    public void createOrder() {
        order = apiV3.createDefaultOrder();
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

    @CaseId(862)
    @Story("Заказ на доставку")
    @Issue("STF-9456")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            dataProvider = "goods",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Показать заказ по uuid доставке Goods")

    public void getOrder(ApiV3TestData testData) {
        order = apiV3.createOrderDelivery(testData);
        Response response = OrderV3Request.GET(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @CaseId(863)
    @Story("Заказ на самовывоз")
    @Issue("STF-9456")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Показать заказ по uuid самовывоз Metro_Marketplace")

    public void getOrderq(ApiV3TestData testData) {
        order = apiV3.createOrderPickupFromStore(testData);
        Response response = OrderV3Request.GET(order.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}
