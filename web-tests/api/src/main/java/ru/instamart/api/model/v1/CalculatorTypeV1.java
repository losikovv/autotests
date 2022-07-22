
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.request.v1.ShippingMethodsV1Request;

@Data
@EqualsAndHashCode(callSuper=false)
public class CalculatorTypeV1 extends BaseObject {

    @JsonSchema(required = true)
    private String description;
    @JsonSchema(required = true)
    private String name;
    @JsonSchema(required = true)
    private ShippingMethodsV1Request.Preferences preferences;
}
