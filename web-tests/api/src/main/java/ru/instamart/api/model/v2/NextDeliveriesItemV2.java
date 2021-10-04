package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class NextDeliveriesItemV2 extends BaseObject {
    @JsonProperty("store_id")
    private Object storeId;
    private String summary;
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;
    @JsonProperty("start_at")
    private String startsAt;
    private Double price;
    private String kind;
    @JsonProperty("delivery_forecast_text")
    private Object deliveryForecastText;
    private Integer id;
    @JsonProperty("end_at")
    private String endsAt;
}