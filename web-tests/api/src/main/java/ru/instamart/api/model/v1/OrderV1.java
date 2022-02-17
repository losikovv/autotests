package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.PaymentV2;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    private Double total;

    @JsonSchema(required = true)
    @JsonProperty(value = "item_total")
    private Double itemTotal;

    @JsonSchema(required = true)
    @JsonProperty(value = "ship_total")
    private Double shipTotal;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    @JsonProperty(value = "adjustment_total")
    private Double adjustmentTotal;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonSchema(required = true)
    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "updated_at")
    private String updatedAt;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "completed_at")
    private String completedAt;

    @JsonSchema(required = true)
    @JsonProperty(value = "payment_total")
    private Double paymentTotal;

    @JsonSchema(required = true)
    @JsonProperty(value = "shipment_state")
    private Object shipmentState;

    @JsonSchema(required = true)
    @JsonProperty(value = "payment_state")
    private String paymentState;

    @JsonSchema(required = true)
    private String email;

    @JsonSchema(required = true)
    private String token;

    @JsonSchema(required = true)
    private String currency;

    @JsonSchema(required = true)
    @JsonProperty(value = "closing_docs_required")
    private Boolean closingDocsRequired;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "replacement_policy")
    private Object replacementPolicy;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "customer_comment")
    private String customerComment;

    @JsonSchema(required = true)
    @JsonProperty(value = "is_first_order")
    private Boolean isFirstOrder;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "dispatcher_comment")
    private Object dispatcherComment;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "ship_address")
    private AddressV1 shipAddress;

    @JsonSchema(required = true)
    private List<PaymentV2> payments = null;

    @JsonSchema(required = true)
    private List<ShipmentV1> shipments = null;

    @JsonSchema(required = true)
    private List<AdjustmentV1> adjustments = null;

    @Null
    @JsonProperty(value = "line_item_adjustments")
    private List<Object> lineItemAdjustments = null;

    @Null
    @JsonProperty("external_order_mark")
    private Object externalOrderMark;

    ChannelV1 channel;
}
