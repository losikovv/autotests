package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class ProductsV2Request extends ApiV2RequestBase {

    /**
     * Получить продукты
     */
    @Step("{method} /" + ApiV2EndPoints.PRODUCTS)
    public static Response GET(int sid, String query) {
        return givenWithSpec().get(ApiV2EndPoints.PRODUCTS, sid, query);
    }

    /**
     * Получить инфо о продукте
     */
    @Step("{method} /" + ApiV2EndPoints.Products.BY_ID)
    public static Response GET(long productId) {
        return givenWithSpec().get(ApiV2EndPoints.Products.BY_ID, productId);
    }

    @Step("{method} /" + ApiV2EndPoints.Products.BY_SID)
    public static Response GET(final int sid) {
        return givenWithSpec().get(ApiV2EndPoints.Products.BY_SID, sid);
    }
}
