package ru.instamart.api.request.workflows;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.WorkflowsV1Endpoints;
import ru.instamart.api.request.WorkflowsRequestBase;

public class SegmentsRequest extends WorkflowsRequestBase {

    @Step("{method} /" + WorkflowsV1Endpoints.Workflows.Segments.NEXT)
    public static Response PATCH(long workflowId, long segmentId, double lat, double lon) {
        JSONObject body = new JSONObject();
        body.put("lon", lon);
        body.put("lat", lat);
        body.put("segment_id", segmentId);
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(body)
                .patch(WorkflowsV1Endpoints.Workflows.Segments.NEXT, workflowId);
    }
}
