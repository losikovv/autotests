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

    @JsonSchema(required = true)
    @JsonProperty(value = "item_count")
    private Integer itemCount;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "shipped_at")
    private String shippedAt;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private String uuid;

    @JsonSchema(required = true)
    @JsonProperty(value = "order_number")
    private String orderNumber;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "dispatcher_comment")
    private String dispatcherComment;

    @JsonSchema(required = true)
    @JsonProperty(value = "store_id")
    private Integer storeId;

    @JsonSchema(required = true)
    @JsonProperty(value = "store_uuid")
    private String storeUuid;

    @JsonSchema(required = true)
    @JsonProperty(value = "adjustment_total")
    private Double adjustmentTotal;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "payment_state")
    private String paymentState;

    @JsonSchema(required = true)
    @JsonProperty(value = "is_online_payment")
    private Boolean isOnlinePayment;

    @JsonSchema(required = true)
    @JsonProperty(value = "tenant_id")
    private String tenantId;

    @JsonSchema(required = true)
    @JsonProperty(value = "driver_phone")
    private Object driverPhone;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipping_method")
    private ShippingMethodV2 shippingMethod;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "delivery_window")
    private DeliveryWindowV1 deliveryWindow;

    @JsonSchema(required = true)
    private RetailerV1 retailer;

    @JsonSchema(required = true)
    @JsonProperty(value = "retailer_card")
    private Object retailerCard;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipping_category")
    private ShippingCategoryV1 shippingCategory;

    @JsonSchema(required = true)
    @JsonProperty(value = "assembly_notes")
    private List<Object> assemblyNotes = null;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipment_changes")
    private List<Object> shipmentChanges = null;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipping_team_members")
    private List<Object> shippingTeamMembers = null;

    @JsonSchema(required = true)
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;
}
