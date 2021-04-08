package ru.instamart.tests.api.v3.endpoints;

import ru.instamart.api.common.RestBase;
import ru.instamart.api.objects.v3.StoreV3;
import ru.instamart.api.requests.v3.StoresV3Request;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

public class StoresV3Test extends RestBase {

    @Test(groups = {"api-instamart-smoke"})
    public void getStores() {
        final Response response = StoresV3Request.Stores.GET();

        response.prettyPeek();

        checkStatusCode200(response);
    }
    @Test(groups = {"api-instamart-smoke"})
    public void getStoresDelivery() {
        final Response response = StoresV3Request.Delivery.GET();

        response.prettyPeek();

        checkStatusCode200(response);
        List<StoreV3> stores = Arrays.asList(response.as(StoreV3[].class));
        String shippingMethodTitle = stores.get(0).getShipping_methods().get(0).getTitle();
        Assert.assertNotNull(shippingMethodTitle);
        Assert.assertNotEquals(shippingMethodTitle, "");
    }
    @Test(groups = {"api-instamart-smoke"})
    public void getStoresPickupFromStore() {
        final Response response = StoresV3Request.PickupFromStore.GET();

        response.prettyPeek();

        checkStatusCode200(response);
    }

}
