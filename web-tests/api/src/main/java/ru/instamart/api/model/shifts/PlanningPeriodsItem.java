package ru.instamart.api.model.shifts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class PlanningPeriodsItem extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("guaranteed_payroll")
    private int guaranteedPayroll;

    @JsonSchema(required = true)
    @JsonProperty("id")
    private int id;
}