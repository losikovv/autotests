package ru.instamart.api.objects.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;
import ru.instamart.api.objects.v2.PaymentV2;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderV1 extends BaseObject {
    private Integer id;
    private String number;
    private Integer total;
    @JsonProperty(value = "item_total")
    private Integer itemTotal;
    @JsonProperty(value = "ship_total")
    private Integer shipTotal;
    private String state;
    @JsonProperty(value = "adjustment_total")
    private Integer adjustmentTotal;
    @JsonProperty(value = "user_id")
    private Object userId;
    @JsonProperty(value = "created_at")
    private String createdAt;
    @JsonProperty(value = "updated_at")
    private String updatedAt;
    @JsonProperty(value = "completed_at")
    private Object completedAt;
    @JsonProperty(value = "payment_total")
    private Integer paymentTotal;
    @JsonProperty(value = "shipment_state")
    private Object shipmentState;
    @JsonProperty(value = "payment_state")
    private String paymentState;
    private String email;
    private String token;
    private String currency;
    @JsonProperty(value = "closing_docs_required")
    private Boolean closingDocsRequired;
    @JsonProperty(value = "replacement_policy")
    private Object replacementPolicy;
    @JsonProperty(value = "customer_comment")
    private Object customerComment;
    @JsonProperty(value = "is_first_order")
    private Boolean isFirstOrder;
    @JsonProperty(value = "dispatcher_comment")
    private Object dispatcherComment;
    @JsonProperty(value = "ship_address")
    private Object shipAddress;
    private List<PaymentV2> payments = null;
    private List<ShipmentV1> shipments = null;
    private List<AdjustmentV1> adjustments = null;
}
