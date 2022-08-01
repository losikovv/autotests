package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;

public class BrandsAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.BRANDS)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.BRANDS);
    }

    @Step("{method} /" + AdminEndpoints.BRANDS)
    public static Response POST(String name, String permalink, String keywords) {
        final Map<String, Object> formParams = new HashMap<>();
        formParams.put("brand[name]", name);
        formParams.put("brand[permalink]", permalink);
        formParams.put("brand[keywords]", keywords);
        return givenWithAuth()
                .formParams(formParams)
                .post(AdminEndpoints.BRANDS);
    }

    @Step("{method} /" + AdminEndpoints.BRAND)
    public static Response PATCH(String name, String permalink, String keywords) {
        final Map<String, Object> formParams = new HashMap<>();
        formParams.put("_method", "patch");
        formParams.put("brand[name]", name);
        formParams.put("brand[permalink]", permalink);
        formParams.put("brand[keywords]", keywords);
        formParams.put("button","");
        return givenWithAuth()
                .formParams(formParams)
                .post(AdminEndpoints.BRAND, permalink);
    }

    @Step("{method} /" + AdminEndpoints.BRAND)
    public static Response DELETE(String permalink) {
        return givenWithAuth()
                .delete(AdminEndpoints.BRAND, permalink);
    }
}
