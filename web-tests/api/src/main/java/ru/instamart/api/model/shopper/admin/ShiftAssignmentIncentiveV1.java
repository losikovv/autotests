package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftAssignmentIncentiveV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private Integer shiftAssignmentId;

    @JsonSchema(required = true)
    private Integer amount;

    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    private String createdBy;

    @JsonSchema(required = true)
    private String createdAt;
}
