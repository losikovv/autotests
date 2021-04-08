package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Order extends BaseObject {
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
    private Address address;
    private Payment payment;
    @JsonProperty(value = "replacement_policy")
    private ReplacementPolicy replacementPolicy;
    private String email;
    private String uuid;
    private List<Shipment> shipments = null;
    @JsonProperty(value = "promotion_codes")
    private List<Object> promotionCodes = null;
    @JsonProperty(value = "shipping_method_kind")
    private String shippingMethodKind;
}
