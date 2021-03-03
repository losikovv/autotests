package instamart.api.action;

import instamart.api.SessionFactory;
import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.HashMap;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Favorites {

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS)
    public static Response GET(final int sid) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + SessionFactory.getSession().getToken())
                .get(ApiV2EndPoints.FavoritesList.ITEMS, sid);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEM)
    public static Response POST(final long id) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + SessionFactory.getSession().getToken())
                .formParams(new HashMap<>(){{put("item[product_id]", id);}})
                .post(ApiV2EndPoints.FavoritesList.ITEM);
    }

    @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEM)
    public static Response DELETE(final long id) {
        return givenCatch()
                .header("Authorization",
                        "Token token=" + SessionFactory.getSession().getToken())
                .delete(ApiV2EndPoints.FavoritesList.ITEM + "/" + id);
    }

    public static class ProductSku {
        @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS_SKU)
        public static Response GET() {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + SessionFactory.getSession().getToken())
                    .get(ApiV2EndPoints.FavoritesList.ITEMS_SKU);
        }

        @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS_SKU)
        public static Response POST(final int sku) {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + SessionFactory.getSession().getToken())
                    .formParams(new HashMap<>(){{put("sku", sku);}})
                    .post(ApiV2EndPoints.FavoritesList.ITEMS_SKU);
        }

        @Step("{method} /" + ApiV2EndPoints.FavoritesList.ITEMS_SKU)
        public static Response DELETE(final int sku) {
            return givenCatch()
                    .header("Authorization",
                            "Token token=" + SessionFactory.getSession().getToken())
                    .delete(ApiV2EndPoints.FavoritesList.ITEMS_SKU + "/" + sku);
        }
    }
 }
