package instamart.api.requests.v3;

import instamart.api.endpoints.ApiV3Endpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public class OrderOptionsDeliveryRequest {
    /**
     * Получение списка опций для заказа с доставкой
     */
    @Step("{method} /" + ApiV3Endpoints.OrderOptions.DELIVERY)
    public static Response PUT () {

        JSONObject ReqParams = new JSONObject();
        JSONArray Items = new JSONArray();
        JSONObject ItemsParams = new JSONObject();
        JSONObject LocationParams = new JSONObject();
        ReqParams.put("retailer_id", "metro");
        ReqParams.put("location", LocationParams);
        ReqParams.put("items", Items);
        Items.add(ItemsParams);
        LocationParams.put("lon", "37.798026");
        LocationParams.put("lat", "55.749309");
        ItemsParams.put("id", "23020");
        ItemsParams.put("quantity", "5");
        ItemsParams.put("price", "300000");
        ItemsParams.put("discount", "0");
        return givenCatch()
                .contentType(ContentType.JSON)

                .body(ReqParams)
                .header("Api-Version","3.0")
                .header("Client-Token","8055cfd11c887f2887dcd109e66dd166")
                .put(ApiV3Endpoints.OrderOptions.DELIVERY);
    }

}
