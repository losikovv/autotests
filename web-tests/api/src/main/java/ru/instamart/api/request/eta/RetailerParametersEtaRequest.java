package ru.instamart.api.request.eta;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.EtaEndpoints;
import ru.instamart.api.request.EtaRequestBase;
import ru.instamart.api.response.eta.RetailerParametersEtaResponse;

public class RetailerParametersEtaRequest extends EtaRequestBase {

    @Step("{method} /" + EtaEndpoints.Retailers.PARAMETERS)
    public static Response GET(String retailerId) {
        return givenWithSpec().get(EtaEndpoints.Retailers.PARAMETERS, retailerId);
    }

    @Step("{method} /" + EtaEndpoints.Retailers.PARAMETERS)
    public static Response PUT(String retailerId, RetailerParametersEtaResponse retailerParameters) {
        return givenWithSpec()
                .contentType(ContentType.JSON)
                .body(retailerParameters)
                .put(EtaEndpoints.Retailers.PARAMETERS, retailerId);
    }

    public static class WithoutСontentType {
        public static Response PUT(String retailerId, RetailerParametersEtaResponse retailerParameters) {
            return givenWithSpec()
                    .body(retailerParameters)
                    .put(EtaEndpoints.Retailers.PARAMETERS, retailerId);
        }
    }
}