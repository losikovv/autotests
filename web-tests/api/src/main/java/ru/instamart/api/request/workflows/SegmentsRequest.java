package ru.instamart.api.request.workflows;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.WorkflowsV1Endpoints;
import ru.instamart.api.request.WorkflowsRequestBase;

public class SegmentsRequest extends WorkflowsRequestBase {

    @Step("{method} /" + WorkflowsV1Endpoints.Workflows.Segments.NEXT)
    public static Response PATCH(long workflowId, SegmentData segmentData) {
        return givenWithAuth()
                .contentType(ContentType.JSON)
                .body(segmentData)
                .patch(WorkflowsV1Endpoints.Workflows.Segments.NEXT, workflowId);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class SegmentData {
        private Double lat;
        private Double lon;
        @JsonProperty("segment_id")
        private Long segmentId;
        @JsonProperty("skip_geo_warn")
        private Boolean skipGeoWarn;
    }
}
