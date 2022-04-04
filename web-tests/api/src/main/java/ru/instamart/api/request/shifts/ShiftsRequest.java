package ru.instamart.api.request.shifts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import ru.instamart.api.endpoint.ShiftsV1Endpoints;
import ru.instamart.api.request.ShiftsRequestBase;
import ru.sbermarket.common.Mapper;

public class ShiftsRequest extends ShiftsRequestBase {

    @Step("{method} /" + ShiftsV1Endpoints.SHIFTS)
    public static Response GET(ShiftQuery query) {
        return givenWithAuth()
                .queryParams(Mapper.INSTANCE.objectToMap(query))
                .get(ShiftsV1Endpoints.SHIFTS);
    }

    @Step("{method} /" + ShiftsV1Endpoints.Shifts.BY_ID)
    public static Response GET(long id) {
        return givenWithAuth()
                .get(ShiftsV1Endpoints.Shifts.BY_ID, id);
    }

    @Step("{method} /" + ShiftsV1Endpoints.Shifts.BY_ID)
    public static Response DELETE(long id) {
        return givenWithAuth()
                .delete(ShiftsV1Endpoints.Shifts.BY_ID, id);
    }

    public static class Start {
        @Step("{method} /" + ShiftsV1Endpoints.Shifts.START)
        public static Response PATCH(long id) {
            return givenWithAuth()
                    .patch(ShiftsV1Endpoints.Shifts.START, id);
        }
    }

    public static class Stop {
        @Step("{method} /" + ShiftsV1Endpoints.Shifts.STOP)
        public static Response POST(long id) {
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
        private final String planningAreaId;
        private final String role;
        @JsonProperty("planning_periods")
        private final PlanningPeriods planningPeriods;
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
