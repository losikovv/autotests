
package ru.instamart.api.model.v3;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class CheckoutOrderV3 extends BaseObject {

    @JsonSchema(required = true)
    private AddressV3 address;

    @JsonSchema(required = true)
    private ContactsV3 contacts;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;

    @JsonSchema(required = true)
    @JsonProperty("item_count")
    private Integer itemCount;

    @JsonSchema(required = true)
    private String number;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("payment_tool")
    private PaymentToolV3 paymentTool;

    @JsonSchema(required = true)
    private List<Object> payments;

    @JsonSchema(required = true)
    @JsonProperty("promo_total")
    private Double promoTotal;

    @JsonSchema(required = true)
    @JsonProperty("promotion_codes")
    private List<Object> promotionCodes;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("replacement_policy")
    private ReplacementPolicyV3 replacementPolicy;

    @JsonSchema(required = true)
    private List<ShipmentV3> shipments;

    @JsonSchema(required = true)
    @JsonProperty("shipping_method_kind")
    private String shippingMethodKind;

    @JsonSchema(required = true)
    @JsonProperty("shipping_methods")
    private List<String> shippingMethods;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    @JsonProperty("total_discount")
    private Double totalDiscount;

    @JsonSchema(required = true)
    @JsonProperty("total_weight")
    private Integer totalWeight;
}
