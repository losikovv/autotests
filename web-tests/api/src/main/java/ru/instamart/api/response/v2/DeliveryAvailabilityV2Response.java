package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.DeliveryAvailabilityV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeliveryAvailabilityV2Response extends BaseResponseObject {

    @JsonProperty("delivery_availability")
    private DeliveryAvailabilityV2 deliveryAvailability;
}
