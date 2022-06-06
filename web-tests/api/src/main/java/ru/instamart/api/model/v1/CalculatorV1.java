package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.request.v1.ShippingMethodsV1Request;

@Data
@EqualsAndHashCode(callSuper=false)
public class CalculatorV1 extends BaseObject {

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private ShippingMethodsV1Request.Preferences preferences;

    @JsonSchema(required = true)
    private CalculatorTypeV1 type;
}
