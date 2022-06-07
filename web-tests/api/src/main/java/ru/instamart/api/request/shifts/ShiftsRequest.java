package ru.instamart.api.request.shifts;

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

import static ru.instamart.kraken.util.TimeUtil.getFutureDateWithoutTime;
import static ru.instamart.kraken.util.TimeUtil.getPastDateWithoutTime;

public class ShiftsRequest extends ShiftsRequestBase {

    @Step("{method} /" + ShiftsV1Endpoints.SHIFTS)
    public static Response GET() {
        return givenWithAuth()
                .queryParam("plan_start_date", getPastDateWithoutTime(7L))
                .queryParam("plan_end_date", getFutureDateWithoutTime(7L))
                .queryParam("state", "new", "ready_to_start", "in_progress", "on_pause")
                .get(ShiftsV1Endpoints.SHIFTS);
    }

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
                    .contentType(ContentType.JSON)
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

    public static class Cancel {
        @Step("{method} /" + ShiftsV1Endpoints.Shifts.CANCEL)
        public static Response PATCH(final long id) {
            JSONObject body = new JSONObject();
            body.put("cancellation_reason", "Технические проблемы");
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .patch(ShiftsV1Endpoints.Shifts.CANCEL, id);
        }
    }

    public static class Pause {
        @Step("{method} /" + ShiftsV1Endpoints.Shifts.PAUSE)
        public static Response POST(final long id) {
            return givenWithAuth()
                    .post(ShiftsV1Endpoints.Shifts.PAUSE, id);
        }
        @Step("{method} /" + ShiftsV1Endpoints.Shifts.PAUSE)
        public static Response POST(final long id, final boolean overlimit) {
            JSONObject json = new JSONObject();
            json.put("overlimit", overlimit);
            return givenWithAuth()
                    .body(json)
                    .contentType(ContentType.JSON)
                    .post(ShiftsV1Endpoints.Shifts.PAUSE, id);
        }

        @Step("{method} /" + ShiftsV1Endpoints.Shifts.PAUSE)
        public static Response DELETE(final long id) {
            return givenWithAuth()
                    .delete(ShiftsV1Endpoints.Shifts.PAUSE, id);
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
        @Singular
        private final List<String> roles;
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
        private final Integer id;
        @JsonProperty("guaranteed_payroll")
        private final int guaranteedPayroll;
    }
}
