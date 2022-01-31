package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReviewableShipmentV2 extends BaseObject {
    @Null
    @JsonSchema(required = true)
    @JsonProperty("on_demand_estimate")
    private Object onDemandEstimate;

    @JsonSchema(required = true)
    @JsonProperty("can_cancel")
    private Boolean canCancel;

    @JsonSchema(required = true)
    @JsonProperty("line_items")
    private List<LineItemsItemV2> lineItems;

    @JsonSchema(required = true)
    @JsonProperty("total_weight")
    private Integer totalWeight;

    @JsonSchema(required = true)
    @JsonProperty("next_deliveries")
    private List<NextDeliveryV2> nextDeliveries;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    private List<Object> discounts;

    @JsonSchema(required = true)
    private PaymentV2 payment;

    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private Boolean checkout;

    @JsonSchema(required = true)
    @JsonProperty("item_count")
    private Integer itemCount;

    @JsonSchema(required = true)
    private List<Object> requirements;

    @JsonSchema(required = true)
    private Double cost;

    @JsonSchema(required = true)
    private RetailerV2 retailer;

    @JsonProperty("shipping_team_members")
    private List<ShippingTeamMembersItemV2> shippingTeamMembers;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("external_partners_service")
    private Object externalPartnersService;

    @JsonSchema(required = true)
    @JsonProperty("item_total")
    private Double itemTotal;

    @JsonSchema(ignore = true)
    private ShipmentStoreV2 store;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("driver_phone")
    private String driverPhone;

    @JsonSchema(required = true)
    private List<AlertV2> alerts;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("driver_name")
    private String driverName;

    @JsonSchema(required = true)
    private List<Object> promotions;

    @JsonSchema(required = true)
    @JsonProperty("item_without_discount_total")
    private Double itemWithoutDiscountTotal;

    @JsonSchema(required = true)
    @JsonProperty("item_count_in_stock")
    private Integer itemCountInStock;

    @JsonSchema(required = true)
    @JsonProperty("delivery_window")
    private DeliveryWindowV2 deliveryWindow;

    @JsonSchema(required = true)
    @JsonProperty("item_discount_total")
    private Double itemDiscountTotal;
}