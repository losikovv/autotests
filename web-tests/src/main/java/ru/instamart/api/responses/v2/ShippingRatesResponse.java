package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.ShippingRate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingRatesResponse extends BaseResponseObject {
    @JsonProperty(value = "shipping_rates")
    private List<ShippingRate> shippingRates = null;
}
