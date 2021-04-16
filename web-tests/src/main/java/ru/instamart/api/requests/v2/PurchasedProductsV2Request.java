package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;
import ru.instamart.api.requests.ApiV2RequestBase;

public final class PurchasedProductsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.PURCHASED_PRODUCTS)
    public static Response GET(final String token, final int sid) {
        return givenCustomToken(token).get(ApiV2EndPoints.PURCHASED_PRODUCTS, sid);
    }
}
