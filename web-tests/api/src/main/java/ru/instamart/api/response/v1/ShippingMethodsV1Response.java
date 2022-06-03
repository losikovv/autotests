package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ShippingMethodV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class ShippingMethodsV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty(value = "shipping_methods")
    private List<ShippingMethodV1> shippingMethods;
}
