package ru.instamart.tests.api.v3.endpoints;

import instamart.api.common.RestBase;
import instamart.api.objects.v3.StoreV3;
import instamart.api.requests.v3.StoresV3Request;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

public class StoresV3Test extends RestBase {

    @Test(groups = {"api-instamart-regress"})
    public void getStores() {
        final Response response = StoresV3Request.GET("delivery");

        response.prettyPeek();

        checkStatusCode200(response);
        List<StoreV3> stores = Arrays.asList(response.as(StoreV3[].class));
        String shippingMethodTitle = stores.get(0).getShipping_methods().get(0).getTitle();
        Assert.assertNotNull(shippingMethodTitle);
        Assert.assertNotEquals(shippingMethodTitle, "");
    }
}
