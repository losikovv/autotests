package ru.instamart.tests.api.v3.endpoints;

import instamart.api.common.RestBase;
import instamart.api.objects.v3.StoreV3;
import instamart.api.requests.v3.OrderOptionsDeliveryRequest;
import instamart.api.requests.v3.OrderOptionsV3Request;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

public class OrderOptionsV3Test extends RestBase {
    StoreV3 store;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        store = apiV3.getStore("METRO, Ленинградское шоссе");
    }

    @Test(groups = {"api-instamart-regress"})
    public void putOrderOptionsPickupFromStore() {
        final Response response = OrderOptionsV3Request.PickupFromStore.PUT(
                "metro",
                store.getId());

        response.prettyPeek();
    }
    @Test(groups = {"api-instamart-regress"})
    public void putOrderOptionsDelivery() {
        final Response response = OrderOptionsDeliveryRequest.PUT();

        response.prettyPeek();

        checkStatusCode200(response);
    }
}
