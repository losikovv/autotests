package ru.instamart.test.api.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.StoreV3;
import ru.instamart.api.request.v3.OrderOptionsV3Request;
import ru.instamart.api.model.testdata.ApiV3TestData;
import ru.instamart.api.dataprovider.ApiV3DataProvider;

public class OrderOptionsV3Test extends RestBase {
    StoreV3 store;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        store = apiV3.getStore("METRO, Ленинградское шоссе");
    }

    @Test(groups = {"api-instamart-regress"},
          dataProvider = "itemIds",
          dataProviderClass = ApiV3DataProvider.class)
    public void putOrderOptionsPickupFromStore(ApiV3TestData testData) {
        final Response response = OrderOptionsV3Request.PickupFromStore.PUT(
                "metro",
                store.getId(),
                testData.getItemId(),
                testData.getClientToken());


        response.then().statusCode(testData.getStatusCode());

       // OrderOptionsPickupV3Response orderOptionsPickupV3Response = response.as(OrderOptionsPickupV3Response.class);

    }

    @Test(groups = {"api-instamart-regress"})
    public void putOrderOptionsDelivery() {
        final Response response = OrderOptionsV3Request.Delivery.PUT();

        response.then().statusCode(200);
    }
}
