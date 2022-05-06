package ru.instamart.api.request.eta;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.EtaEndpoints;
import ru.instamart.api.request.EtaRequestBase;

public class StoresEtaRequest extends EtaRequestBase {

    public static class Parameters {
        @Step("{method} /" + EtaEndpoints.Stores.PARAMETERS)
        public static Response GET(String storeId) {
            return givenWithSpec().get(EtaEndpoints.Stores.PARAMETERS, storeId);
        }
    }
}
