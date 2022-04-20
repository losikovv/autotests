package ru.instamart.api.response.shifts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.shifts.PolySHP;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlanningAreaShiftsItemSHPResponse extends BaseObject {

    @JsonProperty("surged")
    private boolean surged;

    @JsonProperty("max_fix_payroll_per_hour")
    private int maxFixPayrollPerHour;

    @JsonProperty("name")
    private String name;

    @JsonProperty("region_id")
    private int regionId;

    @JsonProperty("poly")
    private PolySHP poly;

    @JsonProperty("id")
    private int id;
}