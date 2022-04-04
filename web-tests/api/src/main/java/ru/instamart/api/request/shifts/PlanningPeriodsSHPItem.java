package ru.instamart.api.request.shifts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlanningPeriodsSHPItem extends BaseObject {

    @JsonProperty("base_predicted_payroll")
    private int basePredictedPayroll;

    @JsonProperty("base_guaranteed_payroll")
    private int baseGuaranteedPayroll;

    @JsonProperty("surged")
    private boolean surged;

    @JsonProperty("started_at")
    private String startedAt;

    @JsonProperty("id")
    private int id;

    @JsonProperty("ended_at")
    private String endedAt;

    @JsonProperty("plan_peoples_count")
    private int planPeoplesCount;
}