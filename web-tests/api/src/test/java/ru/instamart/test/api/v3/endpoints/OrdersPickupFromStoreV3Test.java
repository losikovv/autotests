package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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
public class OrdersPickupFromStoreV3Test extends RestBase {

    OrderV3 orderMetroMarketplace;
    OrderV3 orderAuchan;

    @CaseId(861)
    @Story("Самовывоз")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "metro_marketplace",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Создание заказа на самовывоз Metro_Marketplace")
    public void postOrdersPickupFromStoreMetroMarketplace(ApiV3TestData testData) {

        orderMetroMarketplace = apiV3.createOrderPickupFromStore(testData);

        response = OrderV3Request.GET(orderMetroMarketplace.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderMetroMarketplace.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }

    @CaseId(1918)
    @Story("Самовывоз")
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "Auchan",
            dataProviderClass = ApiV3DataProvider.class,
            description = "Создание заказа на самовывоз Auchan")
    public void postOrdersPickupFromStoreAuchan(ApiV3TestData testData) {

        orderAuchan = apiV3.createOrderPickupFromStore(testData);

        response = OrderV3Request.GET(orderAuchan.getId(), testData.getClientToken());
        checkStatusCode200(response);

        response = OrderV3Request.Cancel.PUT(orderAuchan.getId(), testData.getClientToken());
        checkStatusCode200(response);
    }
}
