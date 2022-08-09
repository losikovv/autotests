package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class Promocode extends BaseObject {
    @JsonProperty(value = "active_until")
    private String activeUntil;

    private String code;

    @JsonProperty(value = "customer_id")
    private String customerId;

    @JsonProperty(value = "discount_amount")
    private int discountAmount;

    private int length;

    @JsonProperty(value = "promotion_id")
    private int promotionId;
    private int usageLimit;
}
