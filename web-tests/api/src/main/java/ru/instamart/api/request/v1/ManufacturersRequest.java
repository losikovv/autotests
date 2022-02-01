package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ManufacturersRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.MANUFACTURERS)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.MANUFACTURERS);
    }

    @Step("{method} /" + AdminEndpoints.MANUFACTURERS)
    public static Response POST(String name) {
        return givenWithAuth()
                .formParam("manufacturer[name]", name)
                .formParam("button", "")
                .post(AdminEndpoints.MANUFACTURERS);
    }


    @Step("{method} /" + AdminEndpoints.Manufacturers.BY_ID)
    public static Response POST(String method, String id, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("_method", method);
        if (Objects.nonNull(name)) {
            params.put("manufacturer[name]", name);
        }
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.Manufacturers.BY_ID, id);
    }


    @Step("{method} /" + AdminEndpoints.Manufacturers.BY_ID)
    public static Response POST(String method, String id) {
        return POST(method, id, null);
    }

}
