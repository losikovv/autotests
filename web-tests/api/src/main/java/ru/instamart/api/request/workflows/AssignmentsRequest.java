package ru.instamart.api.request.workflows;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.WorkflowsV1Endpoints;
import ru.instamart.api.request.WorkflowsRequestBase;

public class AssignmentsRequest extends WorkflowsRequestBase {

    @Step("{method} /" + WorkflowsV1Endpoints.ASSIGNMENTS)
    public static Response GET() {
        return givenWithAuth()
                .get(WorkflowsV1Endpoints.ASSIGNMENTS);
    }

    public static class Accept {
        @Step("{method} /" + WorkflowsV1Endpoints.Assignments.ACCEPT)
        public static Response PATCH(String assignmentId) {
            return givenWithAuth()
                    .patch(WorkflowsV1Endpoints.Assignments.ACCEPT, assignmentId);
        }

        @Step("{method} /" + WorkflowsV1Endpoints.Assignments.ACCEPT)
        public static Response PATCH(String assignmentId, Double lon, Double lat) {
            JSONObject body = new JSONObject();
            body.put("lon", lon);
            body.put("lat", lat);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .patch(WorkflowsV1Endpoints.Assignments.ACCEPT, assignmentId);
        }
    }

    public static class Decline {
        @Step("{method} /" + WorkflowsV1Endpoints.Assignments.DECLINE)
        public static Response PATCH(long assignmentId) {
            return givenWithAuth()
                    .patch(WorkflowsV1Endpoints.Assignments.DECLINE, assignmentId);
        }
    }

    public static class Seen {
        @Step("{method} /" + WorkflowsV1Endpoints.Assignments.SEEN)
        public static Response PATCH(String assignmentId) {
            return givenWithAuth()
                    .patch(WorkflowsV1Endpoints.Assignments.SEEN, assignmentId);
        }
    }
}
