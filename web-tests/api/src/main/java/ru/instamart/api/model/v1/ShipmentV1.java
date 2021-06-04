package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.model.v2.ShippingMethodV2;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentV1 extends BaseObject {
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
    private ShippingMethodV2 shippingMethod;
    @JsonProperty(value = "delivery_window")
    private Object deliveryWindow;
    private RetailerV2 retailer;
    @JsonProperty(value = "retailer_card")
    private Object retailerCard;
    @JsonProperty(value = "shipping_category")
    private ShippingCategoryV1 shippingCategory;
    @JsonProperty(value = "assembly_notes")
    private List<Object> assemblyNotes = null;
    @JsonProperty(value = "shipment_changes")
    private List<Object> shipmentChanges = null;
    @JsonProperty(value = "shipping_team_members")
    private List<Object> shippingTeamMembers = null;
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;
}
