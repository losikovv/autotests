package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftAssignmentV1 {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private Integer shiftId;

    @JsonSchema(required = true)
    private Integer shopperId;

    @JsonSchema(required = true)
    private String startsAt;

    @JsonSchema(required = true)
    private String endsAt;

    @JsonSchema(required = true)
    private ShopperV1 shopper;

    @JsonSchema(required = true)
    private List<ShiftAssignmentIncentiveV1> shiftAssignmentIncentives = null;
}
