package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class TariffV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String title;

    @JsonSchema(required = true)
    private Integer roleId;

    @Null
    @JsonSchema(required = true)
    private Integer operationalZoneId;

    @Null
    @JsonSchema(required = true)
    private Integer retailerId;

    @Null
    @JsonSchema(required = true)
    private String shiftSchedule;

    @Null
    @JsonSchema(required = true)
    private String shiftTimeOfDay;

    @Null
    @JsonSchema(required = true)
    private String shiftStartsAt;

    @Null
    @JsonSchema(required = true)
    private String shiftEndsAt;

    @Null
    @JsonSchema(required = true)
    private String shiftPayroll;

    @Null
    @JsonSchema(required = true)
    private Object shiftPerPositionThreshold;

    @Null
    @JsonSchema(required = true)
    private Object shiftPerPositionPayroll;

    @Null
    @JsonSchema(required = true)
    private Object shiftPerOrderThreshold;

    @Null
    @JsonSchema(required = true)
    private Object shiftPerOrderPayroll;

    @Null
    @JsonSchema(required = true)
    private Object shiftWeightThreshold;

    @Null
    @JsonSchema(required = true)
    private Object shiftWeightPayroll;

    @Null
    @JsonSchema(required = true)
    private Object shiftWeightPerKiloPayroll;

    @JsonSchema(required = true)
    private String createdAt;

    @JsonSchema(required = true)
    private String updatedAt;

    @Null
    @JsonSchema(required = true)
    private String discardedAt;

    @Null
    @JsonSchema(required = true)
    private Integer fine;

    @Null
    @JsonSchema(required = true)
    private Object expectedProfit;

    @Null
    @JsonSchema(required = true)
    private Integer courierPerOrderThreshold;

    @Null
    @JsonSchema(required = true)
    private Double courierPerOrderPayroll;

    @Null
    @JsonSchema(required = true)
    private Integer assemblerPerOrderThreshold;

    @Null
    @JsonSchema(required = true)
    private Double assemblerPerOrderPayroll;

    @Null
    @JsonSchema(required = true)
    private Integer assemblerPerPositionThreshold;

    @Null
    @JsonSchema(required = true)
    private Double assemblerPerPositionPayroll;
}
