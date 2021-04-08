package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.ShippingMethodV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingMethodsV2Response extends BaseResponseObject {
    @JsonProperty(value = "shipping_methods")
    private List<ShippingMethodV2> shippingMethods = null;
}
