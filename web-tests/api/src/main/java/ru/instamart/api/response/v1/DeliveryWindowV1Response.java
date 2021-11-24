package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.DeliveryWindowV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("delivery_window")
    private DeliveryWindowV1 deliveryWindow;
}