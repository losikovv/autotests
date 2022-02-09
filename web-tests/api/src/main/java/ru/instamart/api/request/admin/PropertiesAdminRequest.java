package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;

public class PropertiesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.PROPERTIES)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.PROPERTIES);
    }

    @Step("{method} /" + AdminEndpoints.PROPERTIES)
    public static Response POST(String name, String presentation) {
        final Map<String, Object> formParams = new HashMap<>();
        formParams.put("property[name]", name);
        formParams.put("property[presentation]", presentation);
        return givenWithAuth()
                .formParams(formParams)
                .post(AdminEndpoints.PROPERTIES);
    }

    @Step("{method} /" + AdminEndpoints.PROPERTY)
    public static Response PATCH(String propertyId, String name, String presentation) {
        final Map<String, Object> formParams = new HashMap<>();
        formParams.put("property[name]", name);
        formParams.put("property[presentation]", presentation);
        return givenWithAuth()
                .formParams(formParams)
                .patch(AdminEndpoints.PROPERTY, propertyId);
    }

    @Step("{method} /" + AdminEndpoints.PROPERTY)
    public static Response DELETE(String propertyId) {
        return givenWithAuth()
                .delete(AdminEndpoints.PROPERTY, propertyId);
    }
}
