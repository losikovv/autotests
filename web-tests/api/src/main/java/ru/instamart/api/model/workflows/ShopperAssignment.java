
package ru.instamart.api.model.workflows;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperAssignment extends BaseObject {

    @JsonSchema(required = true)
    private String assembly;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    private String delivery;

    @JsonSchema(required = true)
    @JsonProperty("delivery_type")
    private String deliveryType;

    @JsonSchema(required = true)
    private String distance;

    @JsonSchema(required = true)
    private String duration;

    @JsonSchema(required = true)
    private String id;

    @JsonSchema(required = true)
    @JsonProperty("is_first_order")
    private String isFirstOrder;

    @JsonSchema(required = true)
    @JsonProperty("is_heavy")
    private String isHeavy;

    @JsonSchema(required = true)
    @JsonProperty("items_count")
    private String itemsCount;

    @JsonSchema(required = true)
    @JsonProperty("items_total")
    private String itemsTotal;

    @JsonSchema(required = true)
    @JsonProperty("performer_uuid")
    private String performerUuid;

    @JsonSchema(required = true)
    @JsonProperty("pricing_base_cost")
    private String pricingBaseCost;

    @JsonSchema(required = true)
    @JsonProperty("pricing_bonus_cost")
    private String pricingBonusCost;

    @JsonSchema(required = true)
    @JsonProperty("pricing_total_cost")
    private String pricingTotalCost;

    @JsonSchema(required = true)
    private String status;

    @JsonSchema(required = true)
    @JsonProperty("store_address")
    private String storeAddress;

    @JsonSchema(required = true)
    @JsonProperty("store_name")
    private String storeName;

    @JsonSchema(required = true)
    @JsonProperty("thermal_bag_needed")
    private String thermalBagNeeded;

    @JsonSchema(required = true)
    private String timeout;

    @JsonSchema(required = true)
    private String weight;

    @JsonSchema(required = true)
    @JsonProperty("workflow_id")
    private String workflowId;
}
