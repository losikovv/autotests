package ru.instamart.test.api.surge;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.surge.SurgeRequest;
import ru.instamart.api.response.surge.SurgeResponse;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class SurgeTest extends RestBase {
    private final String storeUuid = "49303be3-8524-4ee2-8e23-17253b8327d0";

    @Test(groups = {"surge-api"})
    public void putStores() {
        Response response = SurgeRequest.Stores.PUT(storeUuid, true);

        response.then().statusCode(anyOf(is(200), is(204)));
    }

    @Test(groups = {"surge-api"},
          dependsOnMethods = "putStores")
    public void getStores() {
        Response response = SurgeRequest.Stores.GET(storeUuid);

        response.then().statusCode(200);
        Assert.assertEquals(response.as(SurgeResponse.class).getData().getDeliveryAreaBaseStoreUUID(), storeUuid);
    }
}
