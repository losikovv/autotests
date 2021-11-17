package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.request.ApiV3RequestBase;

public class ProductsV3Request extends ApiV3RequestBase {

    @Step("{method} /" + ApiV3Endpoints.Stores.PRODUCTS)
    public static Response GET(int sid, int tid){
    return givenMetroMarketPlace()
            .get(ApiV3Endpoints.Stores.PRODUCTS, sid, tid);
    }
}
