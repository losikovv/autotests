
package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class CheckoutStoreV3 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_forecast_text")
    private String deliveryForecastText;

    @JsonSchema(required = true)
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("local_date")
    private String localDate;

    @JsonSchema(required = true)
    @JsonProperty("on_demand")
    private Boolean onDemand;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;
}
