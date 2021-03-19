package ru.instamart.tests.api.v3.endpoints;

import instamart.api.common.RestBase;
import instamart.api.requests.v3.StoresRequest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

public class StoresV3Test extends RestBase {

    @Test(groups = {""})
    public void getStores() {
        final Response response = StoresRequest.GET("delivery");

        response.prettyPeek();

        checkStatusCode200(response);
    }
}
