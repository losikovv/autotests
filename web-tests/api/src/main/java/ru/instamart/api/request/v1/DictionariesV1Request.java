package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class DictionariesV1Request extends ApiV1RequestBase {

    public static class ApiClients {
        @Step("{method} /" + ApiV1Endpoints.Admin.Dictionaries.API_CLIENTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.Dictionaries.API_CLIENTS);
        }
    }

    public static class PaymentMethods {
        @Step("{method} /" + ApiV1Endpoints.Admin.Dictionaries.PAYMENT_METHODS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.Dictionaries.PAYMENT_METHODS);
        }
    }

    public static class PaymentStates {
        @Step("{method} /" + ApiV1Endpoints.Admin.Dictionaries.PAYMENT_STATES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.Dictionaries.PAYMENT_STATES);
        }
    }

    public static class ShipmentCombinedStates {
        @Step("{method} /" + ApiV1Endpoints.Admin.Dictionaries.SHIPMENT_COMBINED_STATES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.Dictionaries.SHIPMENT_COMBINED_STATES);
        }
    }

    public static class Tenants {
        @Step("{method} /" + ApiV1Endpoints.Admin.Dictionaries.TENANTS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.Dictionaries.TENANTS);
        }
    }
}
