package ru.instamart.api.request.eta;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.EtaEndpoints;
import ru.instamart.api.request.EtaRequestBase;

public class RetailersEtaRequest extends EtaRequestBase {


    public static class Parameters {
        @Step("{method} /" + EtaEndpoints.Retailers.PARAMETERS)
        public static Response GET(String retailerId) {
            return givenWithSpec().get(EtaEndpoints.Retailers.PARAMETERS, retailerId);
        }
    }
}
