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

    public static class Faq {
        @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQS)
        public static Response GET(Long faqGroupId) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQS, faqGroupId);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQ)
        public static Response GET(Long faqGroupId, Long faqId) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQ, faqGroupId, faqId);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQS)
        public static Response POST(Long faqGroupId, String text, int position) {
            JSONObject body = new JSONObject();
            JSONObject faq = new JSONObject();
            faq.put("question", text);
            faq.put("answer", text);
            faq.put("position", position);
            faq.put("faq_group_id", faqGroupId);
            body.put("faq", faq);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQS, faqGroupId);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQ)
        public static Response PUT(Long faqGroupId, Long faqId, String text, int position) {
            JSONObject body = new JSONObject();
            JSONObject faq = new JSONObject();
            faq.put("question", text);
            faq.put("answer", text);
            faq.put("position", position);
            faq.put("faq_group_id", faqGroupId);
            body.put("faq", faq);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .put(ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQ, faqGroupId, faqId);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQ)
        public static Response DELETE(Long faqGroupId, Long faqId) {
            return givenWithAuth()
                    .delete(ApiV1Endpoints.Admin.FaqGroups.FaqGroup.FAQ, faqGroupId, faqId);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.FaqGroups.FaqGroup.UPDATE_POSITIONS)
        public static Response POST(Long faqGroupId, Long faqId, int positionNumber) {
            JSONObject body = new JSONObject();
            JSONObject position = new JSONObject();
            position.put(faqId, positionNumber);
            body.put("positions", position);
            return givenWithAuth()
                    .body(body)
                    .contentType(ContentType.JSON)
                    .post(ApiV1Endpoints.Admin.FaqGroups.FaqGroup.UPDATE_POSITIONS, faqGroupId);
        }
    }
}
