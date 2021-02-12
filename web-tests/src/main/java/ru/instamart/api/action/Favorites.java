package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Favorites {

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response getAllFavoriteItems(final String token, final int sid) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .get(ApiV2EndPoints.FavoritesList.ITEMS, sid);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS_SKU)
    public static Response getAllFavoriteItemsBySku(final String token) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .get(ApiV2EndPoints.FavoritesList.ITEMS_SKU);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEM)
    public static Response addFavoriteItem(final String token, final int id) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .formParams(new HashMap<>(){{put("item[product_id]", id);}})
                .post(ApiV2EndPoints.FavoritesList.ITEM);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS_SKU)
    public static Response addFavoriteItemBySku(final String token, final int sku) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .formParams(new HashMap<>(){{put("sku", sku);}})
                .post(ApiV2EndPoints.FavoritesList.ITEMS_SKU);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEM)
    public static Response deleteFavoriteItem(final String token, final int id) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .delete(ApiV2EndPoints.FavoritesList.ITEM + "/" + id);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS_SKU)
    public static Response deleteFavoriteItemBySku(final String token, final int sku) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + token)
                .delete(ApiV2EndPoints.FavoritesList.ITEMS_SKU + "/" + sku);
    }
 }
