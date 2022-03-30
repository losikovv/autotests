package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class FaqGroupsV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.Admin.FAQ_GROUPS)
    public static Response POST(String faqGroupName) {
        JSONObject body = new JSONObject();
        JSONObject faqGroup = new JSONObject();
        faqGroup.put("name", faqGroupName);
        body.put("faq_group", faqGroup);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.Admin.FAQ_GROUPS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FAQ_GROUP)
    public static Response DELETE(Long faqGroupId) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.Admin.FaqGroups.FAQ_GROUP, faqGroupId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FAQ_GROUP)
    public static Response PUT(String faqGroupName, Long faqGroupId) {
        JSONObject body = new JSONObject();
        JSONObject faqGroup = new JSONObject();
        faqGroup.put("name", faqGroupName);
        body.put("faq_group", faqGroup);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .put(ApiV1Endpoints.Admin.FaqGroups.FAQ_GROUP, faqGroupId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FAQ_GROUP)
    public static Response GET(Long faqGroupId) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.FaqGroups.FAQ_GROUP, faqGroupId);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.FAQ_GROUPS)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.Admin.FAQ_GROUPS);
    }

    @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.UPDATE_POSITIONS)
    public static Response POST(Long faqGroupId, int positionNumber) {
        JSONObject body = new JSONObject();
        JSONObject position = new JSONObject();
        position.put(faqGroupId, positionNumber);
        body.put("positions", position);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .post(ApiV1Endpoints.Admin.FaqGroups.UPDATE_POSITIONS);
    }
}
