package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.HashMap;

public final class FavoritesV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV2EndPoints.FavoritesList.ITEMS);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response GET(final String sid) {
        return givenWithAuth()
                .queryParam("sid", sid)
                .get(ApiV2EndPoints.FavoritesList.ITEMS, sid);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response GET(final int sid) {
        return givenWithAuth()
                .queryParam("sid", sid)
                .get(ApiV2EndPoints.FavoritesList.ITEMS, sid);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response POST(final long id) {
        return givenWithAuth()
                .formParams(new HashMap<>(){{put("item[product_id]", id);}})
                .post(ApiV2EndPoints.FavoritesList.ITEMS);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response POST(final String id) {
        return givenWithAuth()
                .formParams(new HashMap<>(){{put("item[product_id]", id);}})
                .post(ApiV2EndPoints.FavoritesList.ITEMS);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response DELETE(final long id) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.FavoritesList.ITEMS + "/" + id);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response DELETE(final String id) {
        return givenWithAuth()
                .delete(ApiV2EndPoints.FavoritesList.ITEMS + "/" + id);
    }

    public static class ProductSku {
        @Step("{method} /" + ApiV2EndPoints.FavoritesList.PRODUCTS_SKU)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV2EndPoints.FavoritesList.PRODUCTS_SKU);
        }

        @Step("{method} /" + ApiV2EndPoints.FavoritesList.PRODUCTS_SKU)
        public static Response POST(final int sku) {
            return givenWithAuth()
                    .formParams(new HashMap<>(){{put("sku", sku);}})
                    .post(ApiV2EndPoints.FavoritesList.PRODUCTS_SKU);
        }

        @Step("{method} /" + ApiV2EndPoints.FavoritesList.PRODUCTS_SKU)
        public static Response DELETE(final int sku) {
            return givenWithAuth()
                    .delete(ApiV2EndPoints.FavoritesList.PRODUCTS_SKU + "/" + sku);
        }
    }
 }
