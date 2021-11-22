
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreditCardAuthorizationV2 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    @JsonProperty("credit_card")
    private CreditCardV2 creditCard;

    @JsonSchema(required = true)
    @JsonProperty("transaction_number")
    private String transactionNumber;
}
