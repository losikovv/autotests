package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class ExternalPartnersV2Request extends ApiV2RequestBase {

    public static class Banners {
        public static class SberPrime {
            @Step("{method} /" + ApiV2EndPoints.ExternalPartners.Banners.SBER_PRIME)
            public static Response GET(final String sid) {
                return givenWithAuth()
                        .queryParam("store_id", sid)
                        .get(ApiV2EndPoints.ExternalPartners.Banners.SBER_PRIME);
            }
        }
    }

    public static class Services {
        @Step("{method} /" + ApiV2EndPoints.ExternalPartners.SERVICES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.ExternalPartners.SERVICES);
        }
    }
}
