package ru.instamart.api.model.shadowcat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.instamart.api.model.BaseObject;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderSC extends BaseObject {

    @JsonProperty("delivery_method")
    private String deliveryMethod;

    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("store_front")
    private String storeFront;

    @JsonProperty("delivery_price")
    private Double deliveryPrice;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("total_price")
    private Double totalPrice;

    @JsonProperty("is_b2b_order")
    private boolean isB2b;

    @JsonProperty("promo_code")
    private String promo_code;

    private CustomerSC customer;

    private List<ShipmentSC> shipments;
}

