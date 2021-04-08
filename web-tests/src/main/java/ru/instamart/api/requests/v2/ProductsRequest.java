package ru.instamart.api.requests.v2;

import ru.instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class ProductsRequest {

    /**
     * Получить продукты
     */
    @Step("{method} /" + ApiV2EndPoints.PRODUCTS)
    public static Response GET(int sid, String query) {
        return givenCatch().get(ApiV2EndPoints.PRODUCTS, sid, query);
    }

    /**
     * Получить инфо о продукте
     */
    @Step("{method} /" + ApiV2EndPoints.Products.ID)
    public static Response GET(long productId) {
        return givenCatch().get(ApiV2EndPoints.Products.ID, productId);
    }
}
