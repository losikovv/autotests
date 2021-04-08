package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Shipment extends BaseObject {
    private Integer id;
    private String number;
    private Double cost;
    @JsonProperty(value = "item_total")
    private Double itemTotal;
    private Double total;
    private String state;
    private List<Alert> alerts = null;
    private List<Promotion> promotions = null;
    @JsonProperty(value = "next_deliveries")
    private List<NextDelivery> nextDeliveries = null;
    @JsonProperty(value = "line_items")
    private List<LineItem> lineItems = null;
    @JsonProperty(value = "delivery_window")
    private DeliveryWindow deliveryWindow;
    private Retailer retailer;
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
