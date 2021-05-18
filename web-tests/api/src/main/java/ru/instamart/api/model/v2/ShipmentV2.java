package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentV2 extends BaseObject {
    private Integer id;
    private String number;
    private Double cost;
    @JsonProperty(value = "item_total")
    private Double itemTotal;
    private Double total;
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
    private List<Object> requirements = null;
}
