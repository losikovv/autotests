package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.ShippingMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingMethodsResponse extends BaseResponseObject {
    @JsonProperty(value = "shipping_methods")
    private List<ShippingMethod> shippingMethods = null;
}
