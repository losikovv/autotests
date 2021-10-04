package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;


@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryWindowsItemV2 extends BaseObject {
    @JsonProperty("item_count_balance")
    private Integer itemsCountBalance;
    @JsonProperty("start_at")
    private String startsAt;
    private String kind;
    @JsonProperty("delivery_forecast_text")
    private Object deliveryForecastText;
    private Object lifetime;
    private Integer id;
    @JsonProperty("end_at")
    private String endsAt;
    @JsonProperty("weight_balance")
    private Integer weightBalance;
    @JsonProperty("is_express_delivery")
    private Boolean isExpressDelivery;
}