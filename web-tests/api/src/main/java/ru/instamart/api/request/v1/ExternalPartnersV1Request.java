package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class ExternalPartnersV1Request extends ApiV1RequestBase {

    public static class Services {
        @Step("{method} /" + ApiV1Endpoints.ExternalPartners.SERVICES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.ExternalPartners.SERVICES);
        }
    }

    public static class Banners {
        public static class SberPrime {
            @Step("{method} /" + ApiV1Endpoints.ExternalPartners.Banners.SBER_PRIME)
            public static Response GET() {
                return givenWithAuth()
                        .get(ApiV1Endpoints.ExternalPartners.Banners.SBER_PRIME);
            }
        }
    }
}
