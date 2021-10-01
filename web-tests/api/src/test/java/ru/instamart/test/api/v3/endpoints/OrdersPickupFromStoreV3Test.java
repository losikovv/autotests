package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.ApiV3DataProvider;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.model.v3.OrderV3;
import ru.instamart.api.model.v3.StoreV3;
import ru.instamart.api.request.v3.OrderV3Request;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Создание заказа")

public class OrdersPickupFromStoreV3Test extends RestBase {

    StoreV3 store;
    OrderV3 orderMetroMarketplace;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        store = apiV3.getStore("METRO, Щелковская");
    }

    @CaseId(861)
    @Story("Самовывоз")
    @Issue("DVR-1547")
    @Test(enabled = false,
            groups = {"api-instamart-regress"},
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
}
