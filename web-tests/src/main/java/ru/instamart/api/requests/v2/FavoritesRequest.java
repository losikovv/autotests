package instamart.api.requests.v2;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static instamart.api.requests.InstamartRequestsBase.givenWithAuthApiV2;

public final class FavoritesRequest {

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS_SID)
    public static Response GET(final int sid) {
        return givenWithAuthApiV2()
                .get(ApiV2EndPoints.FavoritesList.ITEMS, sid);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response POST(final long id) {
        return givenWithAuthApiV2()
                .formParams(new HashMap<>(){{put("item[product_id]", id);}})
                .post(ApiV2EndPoints.FavoritesList.ITEMS);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response DELETE(final long id) {
        return givenWithAuthApiV2()
                .delete(ApiV2EndPoints.FavoritesList.ITEMS + "/" + id);
    }

    public static class ProductSku {
        @Step("{method} /" + ApiV2EndPoints.FavoritesList.PRODUCTS_SKU)
        public static Response GET() {
            return givenWithAuthApiV2()
                    .get(ApiV2EndPoints.FavoritesList.PRODUCTS_SKU);
        }

        @Step("{method} /" + ApiV2EndPoints.FavoritesList.PRODUCTS_SKU)
        public static Response POST(final int sku) {
            return givenWithAuthApiV2()
                    .formParams(new HashMap<>(){{put("sku", sku);}})
                    .post(ApiV2EndPoints.FavoritesList.PRODUCTS_SKU);
        }

        @Step("{method} /" + ApiV2EndPoints.FavoritesList.PRODUCTS_SKU)
        public static Response DELETE(final int sku) {
            return givenWithAuthApiV2()
                    .delete(ApiV2EndPoints.FavoritesList.PRODUCTS_SKU + "/" + sku);
        }
    }
 }
