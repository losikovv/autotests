package ru.instamart.api.model.shifts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.*;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ShiftsV1Endpoints;
import ru.instamart.api.request.ShiftsRequestBase;
import ru.sbermarket.common.Mapper;

import java.util.List;

public class ShiftsRequest extends ShiftsRequestBase {

    @Step("{method} /" + ShiftsV1Endpoints.SHIFTS)
    public static Response GET(final ShiftQuery query) {
        return givenWithAuth()
                .queryParams(Mapper.INSTANCE.objectToMap(query))
                .get(ShiftsV1Endpoints.SHIFTS);
    }

    @Step("{method} /" + ShiftsV1Endpoints.SHIFTS)
    public static Response POST(final PostShift postShift) {
        return givenWithAuth()
                .body(postShift)
                .contentType(ContentType.JSON)
                .post(ShiftsV1Endpoints.SHIFTS);
    }

    @Step("{method} /" + ShiftsV1Endpoints.Shifts.BY_ID)
    public static Response GET(final long id) {
        return givenWithAuth()
                .get(ShiftsV1Endpoints.Shifts.BY_ID, id);
    }

    @Step("{method} /" + ShiftsV1Endpoints.Shifts.BY_ID)
    public static Response DELETE(final long id) {
        return givenWithAuth()
                .delete(ShiftsV1Endpoints.Shifts.BY_ID, id);
    }

    public static class Start {
        @Step("{method} /" + ShiftsV1Endpoints.Shifts.START)
        public static Response PATCH(final long id, final Double lat, final Double lon) {
            JSONObject body = new JSONObject();
            body.put("longitude", lon);
            body.put("latitude", lat);
            return givenWithAuth()
                    .body(body)
                    .patch(ShiftsV1Endpoints.Shifts.START, id);
        }
    }

    public static class Stop {
        @Step("{method} /" + ShiftsV1Endpoints.Shifts.STOP)
        public static Response POST(final long id) {
            return givenWithAuth()
                    .post(ShiftsV1Endpoints.Shifts.STOP, id);
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class ShiftQuery {
        @JsonProperty("shift_date")
        private final String shiftDate;
        private final String state;
        private final String role;
        @JsonProperty("plan_start_date")
        private final String planStartDate;
        @JsonProperty("plan_end_date")
        private final String planEndDate;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class PostShift {
        @JsonProperty("planning_area_id")
        private final Integer planningAreaId;
        private final String role;
        @JsonProperty("planning_periods")
        @Singular
        private final List<PlanningPeriods> planningPeriods;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Builder
    @Getter
    @EqualsAndHashCode
    @ToString
    public static final class PlanningPeriods {
        private final long id;
        @JsonProperty("guaranteed_payroll")
        private final int guaranteedPayroll;
    }
}
