package ru.instamart.api.requests.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoints.ApiV2EndPoints;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCustomToken;

public final class PurchasedProductsV2Request {

    @Step("{method} /" + ApiV2EndPoints.PURCHASED_PRODUCTS)
    public static Response GET(final String token, final int sid) {
        return givenCustomToken(token).get(ApiV2EndPoints.PURCHASED_PRODUCTS, sid);
    }
}
