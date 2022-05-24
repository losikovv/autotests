
package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class CheckoutDeliveryWindowV3 extends BaseObject {

    @JsonSchema(required = true)
    private Boolean available;

    @JsonSchema(required = true)
    @JsonProperty("available_for_tinkoff_dolyame")
    private Boolean availableForTinkoffDolyame;

    @JsonSchema(required = true)
    private String date;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_forecast_text")
    private String deliveryForecastText;

    @JsonSchema(required = true)
    @JsonProperty("ends_at")
    private String endsAt;

    @JsonSchema(required = true)
    @JsonProperty("estimate_minutes_max")
    private Integer estimateMinutesMax;

    @JsonSchema(required = true)
    @JsonProperty("estimate_minutes_min")
    private Integer estimateMinutesMin;

    @JsonSchema(required = true)
    @JsonProperty("estimate_source")
    private String estimateSource;

    @JsonSchema(required = true)
    @JsonProperty("human_interval")
    private String humanInterval;

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    @JsonProperty("is_express_delivery")
    private Boolean isExpressDelivery;

    @JsonSchema(required = true)
    @JsonProperty("items_count_balance")
    private Integer itemsCountBalance;

    @JsonSchema(required = true)
    private String kind;

    @Null
    @JsonSchema(required = true)
    private Object lifetime;

    @JsonSchema(required = true)
    @JsonProperty("starts_at")
    private String startsAt;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;

    @JsonSchema(required = true)
    @JsonProperty("weight_balance")
    private Integer weightBalance;
}
