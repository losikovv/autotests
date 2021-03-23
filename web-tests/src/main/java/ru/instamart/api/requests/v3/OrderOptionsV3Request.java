package instamart.api.requests.v3;

import instamart.api.endpoints.ApiV3Endpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public class OrderOptionsV3Request {

    public static class PickupFromStore {
        /**
         * Применение опций заказа для замовывоза
         */
        @Step("{method} /" + ApiV3Endpoints.OrderOptions.PICKUP_FROM_STORE)
        public static Response PUT(String retailerId, String storeId) {
            JSONObject requestParams = new JSONObject();
            JSONArray items = new JSONArray();
            JSONObject itemParams = new JSONObject();
            requestParams.put("retailer_id", retailerId);
            requestParams.put("store_id", storeId);
            requestParams.put("items", items);
            items.add(itemParams);
            itemParams.put("id", "23022");
            itemParams.put("quantity", 100);
            itemParams.put("price", 1111);
            itemParams.put("discount", 0);
            return givenCatch()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version","3.0")
                    .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                    .put(ApiV3Endpoints.OrderOptions.PICKUP_FROM_STORE);
        }
    }
}
