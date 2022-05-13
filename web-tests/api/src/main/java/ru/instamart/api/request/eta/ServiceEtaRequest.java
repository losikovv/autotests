package ru.instamart.api.request.eta;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.EtaEndpoints;
import ru.instamart.api.request.EtaRequestBase;
import ru.instamart.api.response.eta.ServiceParametersEtaResponse;

public class ServiceEtaRequest extends EtaRequestBase {

    public static class Parameters {
        @Step("{method} /" + EtaEndpoints.Service.PARAMETERS)
        public static Response GET() {
            return givenWithSpec().get(EtaEndpoints.Service.PARAMETERS);
        }

        @Step("{method} /" + EtaEndpoints.Service.PARAMETERS)
        public static Response PUT(ServiceParametersEtaResponse serviceParameters) {
            return givenWithSpec()
                    .contentType(ContentType.JSON)
                    .body(serviceParameters)
                    .put(EtaEndpoints.Service.PARAMETERS);
        }
    }
}
