
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentMethodsStoreV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("payment_method")
    private PaymentMethodV1 paymentMethod;

    @JsonSchema(required = true)
    @JsonProperty("store_id")
    private Long storeId;

    @JsonSchema(required = true)
    @JsonProperty("tenant_id")
    private String tenantId;
}
