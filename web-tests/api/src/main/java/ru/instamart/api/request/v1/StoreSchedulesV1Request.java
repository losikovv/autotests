package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class StoreSchedulesV1Request extends ApiV1RequestBase {

    public static class Schedules {
        @Step("{method} /" + ApiV1Endpoints.Stores.SCHEDULE)
        public static Response GET(String storeUuid, Integer value) {
            return givenWithAuth()
                    .queryParam("per_page", value)
                    .get(ApiV1Endpoints.Stores.SCHEDULE, storeUuid);
        }

        @Step("{method} /" + ApiV1Endpoints.Stores.SCHEDULE)
        public static Response POST(String storeUuid) {
            JSONObject name = new JSONObject();
            JSONObject template = new JSONObject();
            JSONObject delivery_times = new JSONObject();
            JSONArray order = new JSONArray();
            JSONObject body = new JSONObject();

            body.put("end", "02:00");
            body.put("kind", "pickup");
            body.put("orders_limit", "1");
            body.put("shipment_max_kilos", "1");
            body.put("shipment_min_kilos", "0");
            body.put("shipments_excess_items_count", "0");
            body.put("shipments_excess_kilos", "0");
            body.put("start", "00:00");
            body.put("surge_amount", "0");

            order.add(0, body);
            delivery_times.put("delivery_times", order);
            template.put("template", delivery_times);
            name.put("store_schedule", template);

            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(name)
                    .post(ApiV1Endpoints.Stores.SCHEDULE, storeUuid);
        }
    }
}
