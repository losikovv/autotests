package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.model.v2.ShippingMethodV2;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    private Double cost;

    @JsonSchema(required = true)
    @JsonProperty(value = "total_weight")
    private Integer totalWeight;

    @JsonSchema(required = true)
    @JsonProperty(value = "item_total")
    private Double itemTotal;

    @JsonProperty(value = "item_count")
    private Integer itemCount;

    @Null
    @JsonProperty(value = "shipped_at")
    private String shippedAt;

    @JsonSchema(required = true)
    private String state;

    private String uuid;

    @JsonSchema(required = true)
    @JsonProperty(value = "order_number")
    private String orderNumber;

    @Null
    @JsonProperty(value = "dispatcher_comment")
    private String dispatcherComment;

    @JsonSchema(required = true)
    @JsonProperty(value = "store_id")
    private Integer storeId;

    @JsonProperty(value = "store_uuid")
    private String storeUuid;

    @JsonProperty(value = "adjustment_total")
    private Double adjustmentTotal;

    @Null
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

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "delivery_window")
    private DeliveryWindowV1 deliveryWindow;

    @JsonSchema(required = true)
    private RetailerV1 retailer;

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
