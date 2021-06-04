package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ShippingRateV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingRatesV2Response extends BaseResponseObject {
    @JsonProperty(value = "shipping_rates")
    private List<ShippingRateV2> shippingRates = null;
    private Meta meta;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class Meta extends BaseObject {
        @JsonProperty(value = "available_days")
        private List<String> availableDays = null;
    }
}
