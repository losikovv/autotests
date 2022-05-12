package ru.instamart.api.request.eta;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.EtaEndpoints;
import ru.instamart.api.request.EtaRequestBase;
import ru.instamart.api.response.eta.StoreParametersEtaResponse;

public class StoresEtaRequest extends EtaRequestBase {

    @Step("{method} /" + EtaEndpoints.Stores.PARAMETERS)
    public static Response GET(String storeId) {
        return givenWithSpec().get(EtaEndpoints.Stores.PARAMETERS, storeId);
    }

    @Step("{method} /" + EtaEndpoints.Stores.PARAMETERS)
    public static Response PUT(String storeId, StoreParametersEtaResponse storeParameters) {
        return givenWithSpec()
                .contentType(ContentType.JSON)
                .body(storeParameters)
                .put(EtaEndpoints.Stores.PARAMETERS, storeId);
    }
}
