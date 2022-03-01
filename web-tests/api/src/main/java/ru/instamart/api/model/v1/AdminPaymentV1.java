
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminPaymentV1 extends BaseObject {

    @JsonSchema(required = true)
    private Double amount;

    @JsonSchema(required = true)
    @JsonProperty("hold_acquired")
    private Boolean holdAcquired;

    @JsonSchema(required = true)
    @JsonProperty("payment_method")
    private AdminPaymentMethodV1 paymentMethod;
}
