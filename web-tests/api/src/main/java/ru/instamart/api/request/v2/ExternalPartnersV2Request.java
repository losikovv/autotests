package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class ExternalPartnersV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.ExternalPartners.SBER_PRIME)
    public static Response GET(final String idShop) {
        return givenWithAuth()
                .get(ApiV2EndPoints.ExternalPartners.SBER_PRIME, idShop);
    }
}
