package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.ShippingRateV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingRatesV2Response extends BaseResponseObject {
    @JsonProperty(value = "shipping_rates")
    private List<ShippingRateV2> shippingRates = null;
}
