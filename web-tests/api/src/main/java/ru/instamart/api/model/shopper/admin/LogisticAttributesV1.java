package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class LogisticAttributesV1 extends BaseObject {
    @JsonSchema(required = true)
    private Boolean highPriority;

    @Null
    @JsonSchema(required = true)
    private String specifiedDeliveryBefore;

    @Null
    @JsonSchema(required = true)
    private String specifiedDeliveryAfter;

    @JsonSchema(required = true)
    private Boolean doNotRoute;
}
