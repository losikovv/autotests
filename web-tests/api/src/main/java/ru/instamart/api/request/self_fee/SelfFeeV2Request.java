package ru.instamart.api.request.self_fee;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.SelfFreeEndpoints;
import ru.instamart.api.request.SelfFeeRequestBase;

public class SelfFeeV2Request extends SelfFeeRequestBase {

    @Step("{method} /" + SelfFreeEndpoints.V2.Registry.ACCEPT)
    public static Response POST(final Integer id) {
        final var body = new JsonObject();
        final var array = new JsonArray();
        array.add(id);
        body.add("id", array);
        return givenWithAuth()
                .body(body.toString())
                .contentType(ContentType.JSON)
                .post(SelfFreeEndpoints.V2.Registry.ACCEPT);
    }
}
