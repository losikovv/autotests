package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ReviewableShipmentV2 extends BaseObject {
    @JsonProperty("on_demand_estimate")
    private Object onDemandEstimate;
    @JsonProperty("can_cancel")
    private Boolean canCancel;
    @JsonProperty("line_items")
    private List<LineItemsItemV2> lineItems;
    @JsonProperty("total_weight")
    private Integer totalWeight;
    @JsonProperty("next_deliveries")
    private List<NextDeliveryV2> nextDeliveries;
    private String number;
    private Double total;
    private List<Object> discounts;
    private PaymentV2 payment;
    private Integer id;
    private String state;
    private Boolean checkout;
    @JsonProperty("item_count")
    private Integer itemCount;
    private List<Object> requirements;
    private Double cost;
    private RetailerV2 retailer;
    @JsonProperty("shipping_team_members")
    private List<ShippingTeamMembersItemV2> shippingTeamMembers;
    @JsonProperty("external_partners_service")
    private Object externalPartnersService;
    @JsonProperty("item_total")
    private Double itemTotal;
    private StoreV2 store;
    @JsonProperty("driver_phone")
    private String driverPhone;
    private List<AlertV2> alerts;
    @JsonProperty("driver_name")
    private String driverName;
    private List<Object> promotions;
    @JsonProperty("item_without_discount_total")
    private Double itemWithoutDiscountTotal;
    @JsonProperty("item_count_in_stock")
    private Integer itemCountInStock;
    @JsonProperty("delivery_window")
    private DeliveryWindowV2 deliveryWindow;
    @JsonProperty("item_discount_total")
    private Double itemDiscountTotal;
}