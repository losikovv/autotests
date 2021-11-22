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
public class ShipmentV2 extends BaseObject {
    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    private Integer id;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    private String number;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    private Double cost;

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
    @JsonProperty(value = "item_without_discount_total")
    private Double itemWithoutDiscountTotal;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    private Double total;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    private String state;

    @JsonSchema(required = true)
    private List<AlertV2> alerts = null;

    @JsonSchema(required = true)
    private List<PromotionV2> promotions = null;

    @JsonSchema(required = true)
    @JsonProperty(value = "next_deliveries")
    private List<NextDeliveryV2> nextDeliveries = null;

    @JsonSchema(required = true)
    @JsonProperty(value = "line_items")
    private List<LineItemV2> lineItems = null;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "delivery_window")
    private DeliveryWindowV2 deliveryWindow;

    @JsonSchema(required = true)
    private RetailerV2 retailer;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "driver_name")
    private String driverName;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "driver_phone")
    private String driverPhone;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "external_partners_service")
    private String externalPartnersService;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipping_team_members")
    private List<Object> shippingTeamMembers = null;

    @JsonSchema(required = true)
    private List<RequirementV2> requirements = null;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    @JsonProperty(value = "total_weight")
    private Integer totalWeight;

    @JsonSchema(required = true)
    @EqualsAndHashCode.Include
    @JsonProperty(value = "can_cancel")
    private Boolean canCancel;

    @JsonSchema(required = true)
    private List<Object> discounts = null;

    @Null
    @JsonSchema(required = true)
    private PaymentV2 payment;

    @JsonSchema(ignore = true)
    private StoreV2 store;
}
