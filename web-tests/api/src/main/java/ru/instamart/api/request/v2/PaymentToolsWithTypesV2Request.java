package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public class PaymentToolsWithTypesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.PAYMENT_TOOLS_WITH_TYPES)
    public static Response GET(final Integer companyId, final String orderId){
        return givenWithAuth()
                .queryParam("company_id", companyId)
                .queryParam("order_id", orderId)
                .get(ApiV2EndPoints.PAYMENT_TOOLS_WITH_TYPES);
    }
}
