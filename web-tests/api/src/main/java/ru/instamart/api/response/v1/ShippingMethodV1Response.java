
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ShippingMethodV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingMethodV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("shipping_method")
    private ShippingMethodV1 shippingMethod;
}
