package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderV2 extends BaseObject {
    private String number;
    private Double total;
    @JsonProperty(value = "item_count")
    private Integer itemCount;
    @JsonProperty(value = "item_total")
    private Double itemTotal;
    @JsonProperty(value = "ship_total")
    private Double shipTotal;
    @JsonProperty(value = "adjustment_total")
    private Double adjustmentTotal;
    @JsonProperty(value = "promo_total")
    private Double promoTotal;
    @JsonProperty(value = "shipment_state")
    private Object shipmentState;
    @JsonProperty(value = "payment_state")
    private Object paymentState;
    @JsonProperty(value = "special_instructions")
    private String specialInstructions;
    @JsonProperty(value = "created_at")
    private String createdAt;
    @JsonProperty(value = "updated_at")
    private String updatedAt;
    @JsonProperty(value = "completed_at")
    private Object completedAt;
    private AddressV2 address;
    private PaymentV2 payment;
    @JsonProperty(value = "replacement_policy")
    private ReplacementPolicyV2 replacementPolicy;
    private String email;
    private String uuid;
    private List<ShipmentV2> shipments = null;
    @JsonProperty(value = "promotion_codes")
    private List<PromotionCodeV2> promotionCodes = null;
    @JsonProperty(value = "shipping_method_kind")
    private String shippingMethodKind;
}
