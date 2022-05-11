package ru.instamart.api.request.localtor;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.LocatorAppEndpoints;
import ru.instamart.api.request.LocatorRequestBase;


@SuppressWarnings("unchecked")
public class LocatorRequest extends LocatorRequestBase {

    public static class Location {

        @Step("{method} /" + LocatorAppEndpoints.LOCATION)
        public static Response POST(final Double latitude,
                                    final Double longitude,
                                    final Double speed,
                                    final Long timestamp) {
            JSONArray locators = new JSONArray();
            JSONObject locator = new JSONObject();
            locator.put("latitude", latitude);
            locator.put("longitude", longitude);
            locator.put("speed", speed);
            locator.put("time", timestamp);
            locators.add(locator);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(locators)
                    .post(LocatorAppEndpoints.LOCATION);
        }

    }
}
