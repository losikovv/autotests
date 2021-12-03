package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.enums.v2.ProductFilterTypeV2;
import ru.instamart.api.enums.v2.ProductSortTypeV2;
import ru.instamart.api.request.ApiV2RequestBase;

public final class ProductsV2Request extends ApiV2RequestBase {

    /**
     * Получить продукты
     */
    public static Response GET(Integer sid) {
        return GET(sid, (String) null);
    }

    /**
     * Получить продукты
     */
    public static Response GET(Integer sid, String query) {
        return GET(sid, query, (ProductSortTypeV2) null);
    }

    /**
     * Получить продукты
     */
    public static Response GET(Integer sid, Integer tid) {
        return GET(sid, tid, (ProductSortTypeV2) null);
    }

    /**
     * Получить продукты с определенной страницы
     */
    public static Response GET(Integer sid, String query, Integer pageNumber) {
        return GET(sid, query, pageNumber, null, null);
    }

    /**
     * Получить продукты с определенной страницы
     */
    public static Response GET(Integer sid, Integer tid, Integer pageNumber) {
        return GET(sid, tid,  null, null, pageNumber, null, null);
    }

    /**
     * Получить отсортированные продукты
     */
    public static Response GET(Integer sid, String query, ProductSortTypeV2 sort) {
        return GET(sid, null, query, sort, null, null, null);
    }

    public static Response GET(Integer sid, Integer tid, ProductSortTypeV2 sort) {
        return GET(sid, tid, null, sort, null, null, null);
    }

    /**
     * Получить отфильтрованные продукты
     */
    public static Response GET(Integer sid, String query, Integer pageNumber, ProductFilterTypeV2 productFilterType, Integer filterValue) {
        return GET(sid, null, query, null, pageNumber, productFilterType, filterValue);
    }

    @Step("{method} /" + ApiV2EndPoints.PRODUCTS)
    public static Response GET(Integer sid, Integer tid, String query, ProductSortTypeV2 sort, Integer pageNumber, ProductFilterTypeV2 productFilterType, Integer filterNumber) {
        RequestSpecification req = givenWithSpec();

        if (sid != null) req.queryParam("sid", sid);
        if (tid != null) req.queryParam("tid", tid);
        if (query != null) req.queryParam("q", query);
        if (sort != null) req.queryParam("sort", sort.getKey());
        if (pageNumber != null) req.queryParam("page", pageNumber);
        if (productFilterType != null && filterNumber != null) req.queryParam(String.format("filter[%s][]", productFilterType.getValue()), filterNumber);

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
