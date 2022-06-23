package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class SidebarV1 extends BaseObject {

    @Null
    @JsonProperty("tenant_id")
    private String tenantId;

    @Null
    private String email;

    @JsonSchema(required = true)
    @JsonProperty("display_total_weight")
    private String displayTotalWeight;

    @Null
    @JsonProperty("payment_state")
    private String paymentState;

    @Null
    @JsonProperty("installment_plan")
    private InstallmentPlanV1 installmentPlan;

    @JsonSchema(required = true)
    @JsonProperty("item_total")
    private String itemTotal;

    @Null
    @JsonProperty("shipment_state")
    private String shipmentState;

    @JsonSchema(required = true)
    @JsonProperty("replacement_policy")
    private SidebarReplacementPolicyV1 replacementPolicy;

    @Null
    @JsonProperty("completed_at")
    private String completedAt;

    @JsonSchema(required = true)
    private String total;

    @JsonSchema(required = true)
    @JsonProperty("ship_total")
    private String shipTotal;

    @Null
    @JsonProperty("external_order_identifier")
    private String externalOrderIdentifier;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    @JsonProperty("order_platform")
    private String orderPlatform;

    @JsonSchema(required = true)
    private SidebarUserV1 user;

    @JsonSchema(required = true)
    @JsonProperty("adjustment_total")
    private String adjustmentTotal;
}
