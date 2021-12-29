package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class FreeDeliveryV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    private Boolean enabled;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("timer_ends_at")
    private String timerEndsAt;

    @JsonSchema(required = true)
    @JsonProperty("order_is_shipping")
    private Boolean orderIsShipping;
}
