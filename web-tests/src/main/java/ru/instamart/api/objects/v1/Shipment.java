package ru.instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.Retailer;
import ru.instamart.api.objects.v2.ShippingMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class Shipment extends BaseObject {
    private Integer id;
    private String number;
    private Double cost;
    @JsonProperty(value = "total_weight")
    private Integer totalWeight;
    @JsonProperty(value = "item_total")
    private Double itemTotal;
    @JsonProperty(value = "item_count")
    private Integer itemCount;
    @JsonProperty(value = "shipped_at")
    private Object shippedAt;
    private String state;
    private String uuid;
    @JsonProperty(value = "order_number")
    private String orderNumber;
    @JsonProperty(value = "dispatcher_comment")
    private Object dispatcherComment;
    @JsonProperty(value = "store_id")
    private Integer storeId;
    @JsonProperty(value = "store_uuid")
    private String storeUuid;
    @JsonProperty(value = "adjustment_total")
    private Double adjustmentTotal;
    @JsonProperty(value = "payment_state")
    private String paymentState;
    @JsonProperty(value = "is_online_payment")
    private Boolean isOnlinePayment;
    @JsonProperty(value = "tenant_id")
    private String tenantId;
    @JsonProperty(value = "driver_phone")
    private Object driverPhone;
    @JsonProperty(value = "shipping_method")
    private ShippingMethod shippingMethod;
    @JsonProperty(value = "delivery_window")
    private Object deliveryWindow;
    private Retailer retailer;
    @JsonProperty(value = "retailer_card")
    private Object retailerCard;
    @JsonProperty(value = "shipping_category")
    private ShippingCategory shippingCategory;
    @JsonProperty(value = "assembly_notes")
    private List<Object> assemblyNotes = null;
    @JsonProperty(value = "shipment_changes")
    private List<Object> shipmentChanges = null;
    @JsonProperty(value = "shipping_team_members")
    private List<Object> shippingTeamMembers = null;
}
