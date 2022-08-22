package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.model.common.ProductsFilterParams;
import ru.instamart.api.request.ApiV2RequestBase;
import ru.instamart.kraken.common.Mapper;

public final class ProductsV2Request extends ApiV2RequestBase {

    /**
     * Получить продукты
     */
    @Step("{method} /" + ApiV2EndPoints.PRODUCTS)
    public static Response GET(ProductsFilterParams params) {
        return givenWithSpec()
                .queryParams(Mapper.INSTANCE.objectToMap(params))
                .get(ApiV2EndPoints.PRODUCTS);
    }

    /**
     * Получить инфо о продукте
     */
    @Step("{method} /" + ApiV2EndPoints.Products.BY_ID)
    public static Response GET(final Long productId) {
        return givenWithSpec().get(ApiV2EndPoints.Products.BY_ID, productId);
    }

    /**
     * Получить инфо о продукте
     */
    @Step("{method} /" + ApiV2EndPoints.Products.BY_ID)
    public static Response GET(final Long productId, final int sid) {
        return givenWithSpec()
                .queryParam("sid", sid)
                .get(ApiV2EndPoints.Products.BY_ID, productId);
    }
}
