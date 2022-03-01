
package ru.instamart.api.model.v1;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminShipmentV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    @JsonProperty("assembly_comment")
    private String assemblyComment;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("assigned_driver")
    private DriverV1 assignedDriver;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("assigned_shopper")
    private ShopperV1 assignedShopper;

    @JsonSchema(required = true)
    @JsonProperty("combined_state")
    private String combinedState;

    @JsonSchema(required = true)
    @JsonProperty("current_stage")
    private String currentStage;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_window_date")
    private String deliveryWindowDate;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_window_local_ends_at")
    private String deliveryWindowLocalEndsAt;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_window_local_starts_at")
    private String deliveryWindowLocalStartsAt;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_window_time_zone")
    private String deliveryWindowTimeZone;

    @JsonSchema(required = true)
    @JsonProperty("display_total_weight")
    private String displayTotalWeight;

    @JsonSchema(required = true)
    private DriverV1 driver;

    @JsonSchema(required = true)
    @JsonProperty("express_delivery")
    private Boolean expressDelivery;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    private AdminOrderV1 order;

    @JsonSchema(required = true)
    private List<AdminPaymentV1> payments;

    @JsonSchema(required = true)
    @JsonProperty("recent_payment")
    private RecentPaymentV1 recentPayment;

    @JsonSchema(required = true)
    private RetailerV1 retailer;

    @JsonSchema(required = true)
    @JsonProperty("shipment_changes")
    private List<Object> shipmentChanges;

    @JsonSchema(required = true)
    private ShopperV1 shopper;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    @JsonProperty("state_name")
    private String stateName;

    @JsonSchema(required = true)
    private AdminShipmentStoreV1 store;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    private UrlsV1 urls;

    @JsonSchema(required = true)
    private String uuid;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("vehicle_number")
    private String vehicleNumber;
}
