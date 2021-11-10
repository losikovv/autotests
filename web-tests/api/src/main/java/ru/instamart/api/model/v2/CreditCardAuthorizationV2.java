
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CreditCardAuthorizationV2 extends BaseObject {

    @JsonProperty("credit_card")
    private CreditCardV2 creditCard;
    @JsonProperty("transaction_number")
    private String transactionNumber;
}
