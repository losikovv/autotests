package ru.instamart.api.response.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.shadowcat.CustomerSC;
import ru.instamart.api.model.shadowcat.PromotionSC;
import ru.instamart.api.model.shadowcat.ShipmentSC;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalculateResponse extends BaseResponseObject {

    @JsonProperty("delivery_method")
    private String deliveryMethod;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("store_front")
    private String storeFront;

    @JsonProperty("delivery_price")
    private Double deliveryPrice;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("is_b2b_order")
    private boolean isB2b;

    @JsonProperty("promo_code")
    private String promo_code;

    private CustomerSC customer;

    private List<ShipmentSC> shipments;

    private PromotionSC promotion;
}
