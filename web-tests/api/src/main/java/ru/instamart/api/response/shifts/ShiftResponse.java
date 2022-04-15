package ru.instamart.api.response.shifts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shifts.PlanningPeriodsItem;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ShiftResponse extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("role")
    private String role;

    @JsonProperty("guaranteed_payroll")
    private int guaranteedPayroll;

    @JsonSchema(required = true)
    @JsonProperty("fact_started_at")
    private String factStartedAt;

    @JsonSchema(required = true)
    @JsonProperty("fact_ended_at")
    private String factEndedAt;

    @JsonProperty("max_fix_payroll_per_hour")
    private int maxFixPayrollPerHour;

    @JsonSchema(required = true)
    @JsonProperty("planning_area_id")
    private int planningAreaId;

    @JsonSchema(required = true)
    @JsonProperty("pauses_limit_minutes")
    private int pausesLimitMinutes;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    @JsonProperty("planning_area_name")
    private String planningAreaName;

    @JsonSchema(required = true)
    @JsonProperty("partner_id")
    private String partnerId;

    @JsonSchema(required = true)
    @JsonProperty("surged")
    private boolean surged;

    @JsonSchema(required = true)
    @JsonProperty("started_at")
    private String startedAt;

    @JsonSchema(required = true)
    @JsonProperty("id")
    private int id;

    @JsonSchema(required = true)
    @JsonProperty("state")
    private String state;

    @JsonSchema(required = true)
    @JsonProperty("planning_periods")
    private List<PlanningPeriodsItem> planningPeriods;

    @JsonSchema(required = true)
    @JsonProperty("ended_at")
    private String endedAt;
}