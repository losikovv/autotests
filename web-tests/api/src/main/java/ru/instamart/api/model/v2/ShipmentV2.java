package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false, onlyExplicitlyIncluded = true)
public class ShipmentV2 extends BaseObject {
    @EqualsAndHashCode.Include
    private Integer id;
    @EqualsAndHashCode.Include
    private String number;
    @EqualsAndHashCode.Include
    private Double cost;
    @EqualsAndHashCode.Include
    @JsonProperty(value = "item_count")
    private Integer itemCount;
    @EqualsAndHashCode.Include
    @JsonProperty(value = "item_total")
    private Double itemTotal;
    @JsonProperty(value = "item_discount_total")
    private Double itemDiscountTotal;
    @JsonProperty(value = "item_without_discount_total")
    private Double itemWithoutDiscountTotal;
    @EqualsAndHashCode.Include
    private Double total;
    @EqualsAndHashCode.Include
    private String state;
    private List<AlertV2> alerts = null;
    private List<PromotionV2> promotions = null;
    @JsonProperty(value = "next_deliveries")
    private List<NextDeliveryV2> nextDeliveries = null;
    @JsonProperty(value = "line_items")
    private List<LineItemV2> lineItems = null;
    @JsonProperty(value = "delivery_window")
    private DeliveryWindowV2 deliveryWindow;
    private RetailerV2 retailer;
    @JsonProperty(value = "driver_name")
    private String driverName;
    @JsonProperty(value = "driver_phone")
    private String driverPhone;
    @JsonProperty(value = "external_partners_service")
    private String externalPartnersService;
    @JsonProperty(value = "shipping_team_members")
    private List<Object> shippingTeamMembers = null;
    private List<RequirementV2> requirements = null;
    @EqualsAndHashCode.Include
    @JsonProperty(value = "total_weight")
    private Integer totalWeight;
    @EqualsAndHashCode.Include
    @JsonProperty(value = "can_cancel")
    private Boolean canCancel;
    private List<Object> discounts = null;
    private PaymentV2 payment;
}
