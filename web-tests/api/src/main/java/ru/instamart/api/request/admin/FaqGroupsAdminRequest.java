package ru.instamart.api.request.admin;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.AdminEndpoints;
import ru.instamart.api.request.AdminRequestBase;

import java.util.HashMap;
import java.util.Map;

public class FaqGroupsAdminRequest extends AdminRequestBase {

    @Step("{method} /" + AdminEndpoints.FAQ_GROUPS)
    public static Response POST(String faqGroupName) {
        return givenWithAuth()
                .formParam("faq_group[name]", faqGroupName)
                .post(AdminEndpoints.FAQ_GROUPS);
    }

    @Step("{method} /" + AdminEndpoints.FAQ_GROUP)
    public static Response DELETE(Long faqGroupId) {
        return givenWithAuth()
                .formParam("_method", "delete")
                .post(AdminEndpoints.FAQ_GROUP, faqGroupId);
    }

    @Step("{method} /" + AdminEndpoints.FAQ_GROUP)
    public static Response PATCH(String faqGroupName, Long faqGroupId) {
        Map<String, Object> params = new HashMap<>();
        params.put("_method", "patch");
        params.put("faq_group[name]", faqGroupName);
        return givenWithAuth()
                .formParams(params)
                .post(AdminEndpoints.FAQ_GROUP, faqGroupId);
    }

    @Step("{method} /" + AdminEndpoints.FAQ_GROUPS)
    public static Response GET() {
        return givenWithAuth()
                .get(AdminEndpoints.FAQ_GROUPS);
    }
}
