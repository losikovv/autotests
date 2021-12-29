package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class RetailerPositionV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.RETAILER_POSITIONS)
    public static Response PUT(Integer firstRetailerId, Integer secondRetailerId) {
        JSONObject body = new JSONObject();
        JSONObject positions = new JSONObject();
        positions.put(firstRetailerId, 0);
        positions.put(secondRetailerId, 1);
        body.put("positions", positions);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.RETAILER_POSITIONS);
    }
}