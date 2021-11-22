package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class NextDeliveryV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @Null
    @JsonProperty(value = "store_id")
    private Integer storeId;

    @JsonSchema(required = true)
    private Double price;

    @JsonSchema(required = true)
    private String summary;

    @JsonSchema(required = true)
    @JsonProperty(value = "starts_at")
    private String startsAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "ends_at")
    private String endsAt;

    @Null
    @JsonSchema(required = true)
    private String kind;

    @JsonSchema(required = true)
    @JsonProperty(value = "express_delivery")
    private Boolean expressDelivery;

    @JsonProperty("delivery_forecast_text")
    private Object deliveryForecastText;
}
