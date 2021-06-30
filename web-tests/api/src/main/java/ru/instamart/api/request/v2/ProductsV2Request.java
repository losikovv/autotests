package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.request.ApiV2RequestBase;

public final class ProductsV2Request extends ApiV2RequestBase {

    /**
     * Получить продукты
     */
    public static Response GET(Integer sid) {
        return GET(sid, null);
    }

    /**
     * Получить продукты
     */
    public static Response GET(Integer sid, String query) {
        return GET(sid, query, null);
    }

    /**
     * Получить отсортированные продукты
     */
    @Step("{method} /" + ApiV2EndPoints.PRODUCTS)
    public static Response GET(Integer sid, String query, ProductSortTypeV2 sort) {
        RequestSpecification req = givenWithSpec();

        if (sid != null) req.queryParam("sid", sid);
        if (query != null) req.queryParam("q", query);
        if (sort != null) req.queryParam("sort", sort.getKey());

        return req.get(ApiV2EndPoints.PRODUCTS);
    }

    /**
     * Получить инфо о продукте
     */
    @Step("{method} /" + ApiV2EndPoints.Products.BY_ID)
    public static Response GET(Long productId) {
        return givenWithSpec().get(ApiV2EndPoints.Products.BY_ID, productId);
    }
}
