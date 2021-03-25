package ru.instamart.tests.api.v3.endpoints;

import instamart.api.common.RestBase;
import instamart.api.requests.v3.SetupInfoV3Request;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

public class SetupInfoV3Test extends RestBase {

    @Test(groups = {"api-instamart-smoke"})
    public void getSetupInfo() {
        final Response response = SetupInfoV3Request.GET();

        response.prettyPeek();

        checkStatusCode200(response);
    }
    @Test(groups = {"api-instamart-smoke"})
    public void getSetupInfoStores() {
        final Response response = SetupInfoV3Request.Stores.GET();

        response.prettyPeek();

        checkStatusCode200(response);
    }
}
