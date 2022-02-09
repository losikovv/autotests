package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;

public class TaxonomiesAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.TAXONOMIES)
    public static Response POST(String taxonomyName) {
        return givenWithAuth()
                .formParam("taxonomy[name]", taxonomyName)
                .post(AdminEndpoints.TAXONOMIES);
    }

    @Step("{method} /" + AdminEndpoints.TAXONOMY)
    public static Response DELETE(Long taxonomyId) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .post(AdminEndpoints.TAXONOMY, taxonomyId);
    }

    @Step("{method} /" + AdminEndpoints.TAXONOMY)
    public static Response PATCH(String taxonomyName, Long taxonomyId) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "patch");
        params.put("taxonomy[name]", taxonomyName);
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.TAXONOMY, taxonomyId);
    }

    @Step("{method} /" + AdminEndpoints.TAXONOMIES)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.TAXONOMIES);
    }
}
