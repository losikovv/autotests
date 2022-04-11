package ru.instamart.api.request.workflows;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.WorkflowsV1Endpoints;
import ru.instamart.api.request.WorkflowsRequestBase;

public class WorkflowsRequest extends WorkflowsRequestBase {

    @Step("{method} /" + WorkflowsV1Endpoints.WORKFLOWS)
    public static Response GET() {
        return givenWithAuth()
                .get(WorkflowsV1Endpoints.WORKFLOWS);
    }
}
