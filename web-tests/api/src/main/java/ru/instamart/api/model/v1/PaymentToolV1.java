
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolV1 extends BaseObject {

    @JsonSchema(required = true)
    private Boolean disabled;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("payment_method")
    private OrderPaymentMethodV1 paymentMethod;

    @Null
    @JsonSchema(required = true)
    private Object source;
}
