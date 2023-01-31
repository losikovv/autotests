package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryForecastV1 extends BaseObject {

    @JsonProperty("will_deliver_at")
    private String willDeliverAt;

    @JsonProperty("has_delay")
    private boolean hasDelay;
}