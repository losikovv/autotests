package ru.instamart.api.request.workflows;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.WorkflowsV1Endpoints;
import ru.instamart.api.request.WorkflowsRequestBase;

public class AssignmentsRequest extends WorkflowsRequestBase {

    @Step("{method} /" + WorkflowsV1Endpoints.ASSIGNMENTS)
    public static Response GET() {
        return givenWithAuth()
                .get(WorkflowsV1Endpoints.ASSIGNMENTS);
    }

    public static class Seen {
        @Step("{method} /" + WorkflowsV1Endpoints.Assignments.SEEN)
        public static Response PATCH(String assignmentId) {
            return givenWithAuth()
                    .patch(WorkflowsV1Endpoints.Assignments.SEEN, assignmentId);
        }
    }

    public static class Accept {
        @Step("{method} /" + WorkflowsV1Endpoints.Assignments.ACCEPT)
        public static Response PATCH(String assignmentId) {
            return givenWithAuth()
                    .patch(WorkflowsV1Endpoints.Assignments.ACCEPT, assignmentId);
        }
    }
}
