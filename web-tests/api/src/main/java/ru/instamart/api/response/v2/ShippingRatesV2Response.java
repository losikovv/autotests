package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ShippingRateV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingRatesV2Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty(value = "shipping_rates")
    private List<ShippingRateV2> shippingRates = null;

    @JsonSchema(required = true)
    private Meta meta;

    @Data
    @EqualsAndHashCode(callSuper=false)
    public static class Meta extends BaseObject {
        @JsonSchema(required = true)
        @JsonProperty(value = "available_days")
        private List<String> availableDays = null;
    }
}
