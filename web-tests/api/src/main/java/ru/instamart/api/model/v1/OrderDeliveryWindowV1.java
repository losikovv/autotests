
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderDeliveryWindowV1 extends BaseObject {

    @JsonSchema(required = true)
    private Boolean available;

    @JsonSchema(required = true)
    private String date;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_forecast_text")
    private Object deliveryForecastText;

    @JsonSchema(required = true)
    @JsonProperty("ends_at")
    private String endsAt;

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
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("invoice_payment_available")
    private Boolean invoicePaymentAvailable;

    @JsonSchema(required = true)
    @JsonProperty("items_count_balance")
    private Integer itemsCountBalance;

    @JsonSchema(required = true)
    private String kind;

    @Null
    @JsonSchema(required = true)
    private Object lifetime;

    @JsonSchema(required = true)
    private Integer serial;

    @JsonSchema(required = true)
    @JsonProperty("starts_at")
    private String startsAt;

    @JsonSchema(required = true)
    @JsonProperty("surge_amount")
    private Double surgeAmount;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;

    @JsonSchema(required = true)
    @JsonProperty("weight_balance")
    private Integer weightBalance;
}
