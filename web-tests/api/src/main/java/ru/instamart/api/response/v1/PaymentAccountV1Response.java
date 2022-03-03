
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.PaymentAccountV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentAccountV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("payment_account")
    private PaymentAccountV1 paymentAccount;
}
