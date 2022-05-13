package ru.instamart.api.response.shifts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.shifts.PolySHP;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlanningAreaShiftsItemSHPResponse extends BaseObject {

    @JsonProperty("surged")
    @JsonSchema(required = true)
    private boolean surged;

    @JsonProperty("max_fix_payroll_per_hour")
    @JsonSchema(required = true)
    private int maxFixPayrollPerHour;

    @JsonProperty("name")
    @JsonSchema(required = true)
    private String name;

    @JsonProperty("region_id")
    @JsonSchema(required = true)
    private int regionId;

    @JsonProperty("poly")
    @JsonSchema(required = true)
    private PolySHP poly;

    @JsonProperty("id")
    @JsonSchema(required = true)
    private int id;
}