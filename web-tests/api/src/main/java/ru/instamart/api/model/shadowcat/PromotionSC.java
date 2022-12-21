package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PromotionSC extends BaseObject {

    @JsonProperty("adjustment_price")
    private String adjustmentPrice;

    @JsonProperty("adjustment_amount")
    private String adjustmentAmount;

    @JsonProperty("free_delivery_amount")
    private String freeDeliveryAmount;
}
