package ru.instamart.api.response.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.OrderPaymentMethodV3;
import ru.instamart.api.model.v3.PaymentToolV3;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentToolsV3Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("payment_tools")
    private List<PaymentToolV3> paymentTools;

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("payment_methods")
    private List<OrderPaymentMethodV3> paymentMethods;
}
