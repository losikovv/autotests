package ru.instamart.api.response.v1.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.AdminPaymentMethodsV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PaymentMethodsV1Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("payment_methods")
    private List<AdminPaymentMethodsV1> paymentMethods;
}