package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminPaymentMethodV1 extends BaseObject {

    @JsonSchema(required = true)
    private String name;

    @Null
    @JsonSchema(required = true)
    private String description;
}
