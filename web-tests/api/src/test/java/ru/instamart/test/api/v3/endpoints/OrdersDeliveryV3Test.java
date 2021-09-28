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

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Создание заказа")

public class OrdersDeliveryV3Test extends RestBase {

    OrderV3 orderGoods;
    OrderV3 orderMetroMarketplace;
    OrderV3 orderSberDevices;

    @CaseId(858)
    @Story("Доставка")
    @Issue("STF-9456")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "goods",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Создание заказа на доставку Goods")
    public void postOrderDeliveryGoods(ApiV3TestData testData) {

        orderGoods = apiV3.createOrderDelivery(testData);

        response = OrderV3Request.GET(orderGoods.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderGoods.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @CaseId(860)
    @Story("Доставка")
    @Issue("STF-9456")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "sber_devices",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Создание на доставку заказа Sber_devices")
    public void postOrderDeliverySberDevices(ApiV3TestData testData) {

        orderSberDevices = apiV3.createOrderDelivery(testData);
        checkStatusCode200(response);

        response = OrderV3Request.GET(orderSberDevices.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderSberDevices.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }


    @CaseId(859)
    @Story("Доставка")
    @Issue("STF-9456")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Создание заказа на доставку Metro_Marketplace")

    public void postOrderDeliveryMetroMarketplace(ApiV3TestData testData) {

        orderMetroMarketplace = apiV3.createOrderDelivery(testData);
        checkStatusCode200(response);

        response = OrderV3Request.GET(orderMetroMarketplace.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderMetroMarketplace.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}


