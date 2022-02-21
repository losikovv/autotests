
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.shopper.admin.ReplacementPolicyV1;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserShipmentV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("can_cancel")
    private Boolean canCancel;

    @Null
    @JsonSchema(required = true)
    private DelayV1 delay;

    @JsonSchema(required = true)
    @JsonProperty("delivery_window")
    private UserShipmentDeliveryWindowV1 deliveryWindow;

    @JsonSchema(required = true)
    @JsonProperty("delivery_window_id")
    private Long deliveryWindowId;

    @JsonSchema(required = true)
    @JsonProperty("document_types")
    private List<String> documentTypes;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("driver_name")
    private String driverName;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("driver_phone")
    private String driverPhone;

    @JsonSchema(required = true)
    private String email;

    @JsonSchema(required = true)
    @JsonProperty("external_partners_service")
    private Object externalPartnersService;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;

    @JsonSchema(required = true)
    @JsonProperty("is_b2b_card_closing_documents_avaible")
    private Boolean isB2bCardClosingDocumentsAvaible;

    @JsonSchema(required = true)
    @JsonProperty("items_cost")
    private Double itemsCost;

    @JsonSchema(required = true)
    @JsonProperty("items_quantity")
    private Long itemsQuantity;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    @JsonProperty("order_number")
    private String orderNumber;

    @JsonSchema(required = true)
    @JsonProperty("payment_method")
    private PaymentMethodV1 paymentMethod;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("previous_delivery_window")
    private UserShipmentDeliveryWindowV1 previousDeliveryWindow;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("promo_code")
    private String promoCode;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("promo_code_description")
    private String promoCodeDescription;

    @JsonSchema(required = true)
    @JsonProperty("promo_total")
    private Double promoTotal;

    @JsonSchema(required = true)
    @JsonProperty("repeat_allowed")
    private Boolean repeatAllowed;

    @JsonSchema(required = true)
    @JsonProperty("replacement_policy")
    private ReplacementPolicyV1 replacementPolicy;

    @JsonSchema(required = true)
    @JsonProperty("retailer_id")
    private Long retailerId;

    @JsonSchema(required = true)
    private Object review;

    @JsonSchema(required = true)
    @JsonProperty("ship_address")
    private ShipAddressV1 shipAddress;

    @JsonSchema(required = true)
    @JsonProperty("shipping_method_kind")
    private String shippingMethodKind;

    @JsonSchema(required = true)
    @JsonProperty("shipping_team_members")
    private List<Object> shippingTeamMembers;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("shopper_name")
    private String shopperName;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("shopper_phone")
    private String shopperPhone;

    @JsonSchema(required = true)
    @JsonProperty("spasibo_amount")
    private Double spasiboAmount;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private UserShipmentStoreV1 store;

    @JsonSchema(required = true)
    @JsonProperty("store_id")
    private Integer storeId;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    @JsonProperty("total_cost")
    private Double totalCost;

    @JsonSchema(required = true)
    @JsonProperty("total_weight")
    private Long totalWeight;
}
