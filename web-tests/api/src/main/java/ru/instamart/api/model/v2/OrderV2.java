package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)
public class OrderV2 extends BaseObject {
    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    private String number;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    private Double total;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    @JsonProperty(value = "item_count")
    private Integer itemCount;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    @JsonProperty(value = "item_total")
    private Double itemTotal;

    @JsonSchema(required = true)
    @JsonProperty(value = "item_discount_total")
    private Double itemDiscountTotal;

    @JsonSchema(required = true)
    @JsonProperty(value = "ship_total")
    private Double shipTotal;

    @JsonSchema(required = true)
    @JsonProperty(value = "adjustment_total")
    private Double adjustmentTotal;

    @JsonSchema(required = true)
    @JsonProperty(value = "promo_total")
    private Double promoTotal;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "shipment_state")
    private String shipmentState;

    @JsonSchema(required = true)
    @JsonProperty(value = "payment_state")
    private Object paymentState;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "special_instructions")
    private String specialInstructions;

    @JsonSchema(required = true)
    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "updated_at")
    private String updatedAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "completed_at")
    private Object completedAt;

    @Null
    @JsonSchema(required = true)
    private AddressV2 address;

    @Null
    @JsonSchema(required = true)
    private PaymentV2 payment;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "replacement_policy")
    private ReplacementPolicyV2 replacementPolicy;

    @Null
    @JsonSchema(required = true)
    private String email;

    @JsonSchema(required = true)
    private String uuid;

    @JsonSchema(required = true)
    private List<ShipmentV2> shipments = null;

    @JsonSchema(required = true)
    @JsonProperty(value = "promotion_codes")
    private List<PromotionCodeV2> promotionCodes = null;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    @JsonProperty(value = "shipping_method_kind")
    private String shippingMethodKind;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    @JsonProperty(value = "total_weight")
    private Double totalWeight;

    @JsonSchema(required = true)
    private List<Object> discounts = null;

    @Null
    @JsonSchema(required = true)
    private CompanyV2 company;
}
