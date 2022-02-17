
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.AlertV2;
import ru.instamart.api.model.v2.NextDeliveryV2;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class MultiretailerOrderShipmentV1 extends BaseObject {

    @JsonSchema(required = true)
    private List<AlertV2> alerts;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("availability_error")
    private Object availabilityError;

    @JsonSchema(required = true)
    private Double cost;

    @JsonSchema(required = true)
    @JsonProperty("delivery_days")
    private List<String> deliveryDays;

    @Null
    @JsonProperty("delivery_window")
    private DeliveryWindowV1 deliveryWindow;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("delivery_window_id")
    private Long deliveryWindowId;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("is_alcohol")
    private Boolean isAlcohol;

    @JsonSchema(required = true)
    @JsonProperty("item_total")
    private Double itemTotal;

    @JsonSchema(required = true)
    @JsonProperty("items_quantity")
    private Integer itemsQuantity;

    @JsonSchema(required = true)
    @JsonProperty("line_items")
    private List<LineItemV1> lineItems;

    @JsonSchema(required = true)
    @JsonProperty("next_deliveries")
    private List<NextDeliveryV2> nextDeliveries;

    @JsonSchema(required = true)
    private String number;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("on_demand_estimate")
    private Object onDemandEstimate;

    @JsonSchema(required = true)
    @JsonProperty("order_id")
    private Long orderId;

    @JsonSchema(required = true)
    @JsonProperty("order_number")
    private String orderNumber;

    @JsonSchema(required = true)
    private List<Object> payments;

    @JsonSchema(required = true)
    private List<Object> promotions;

    @JsonSchema(required = true)
    private RetailerV1 retailer;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private StoreV1 store;

    @JsonSchema(required = true)
    @JsonProperty("store_id")
    private Integer storeId;

    @JsonSchema(required = true)
    @JsonProperty("store_local_date")
    private String storeLocalDate;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    @JsonProperty("total_weight")
    private Integer totalWeight;

    @JsonSchema(required = true)
    @JsonProperty("updated_at")
    private String updatedAt;
}
