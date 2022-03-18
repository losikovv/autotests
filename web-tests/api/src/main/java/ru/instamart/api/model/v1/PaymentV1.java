
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v1.PaymentMethodV1;
import ru.instamart.api.model.v1.ShortShipmentV1;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentV1 extends BaseObject {

    @JsonSchema(required = true)
    private List<Object> actions;

    @JsonSchema(required = true)
    private Double amount;

    @JsonSchema(required = true)
    @JsonProperty("corporate_card")
    private Boolean corporateCard;

    @JsonSchema(required = true)
    @JsonProperty("created_at")
    private String createdAt;

    @JsonSchema(required = true)
    @JsonProperty("display_amount")
    private String displayAmount;

    @JsonSchema(required = true)
    @JsonProperty("hold_acquired")
    private Boolean holdAcquired;

    @JsonSchema(required = true)
    @JsonProperty("hold_amount")
    private Double holdAmount;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String identifier;

    @JsonSchema(required = true)
    @JsonProperty("payment_method")
    private PaymentMethodV1 paymentMethod;

    @JsonSchema(required = true)
    @JsonProperty("payment_method_id")
    private Long paymentMethodId;

    @JsonSchema(required = true)
    private List<ShortShipmentV1> shipments;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    @JsonProperty("state_with_authorization")
    private String stateWithAuthorization;
}
