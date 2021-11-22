
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreTenantV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    private String hostname;

    @JsonSchema(required = true)
    private String id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty("preferred_card_payment_method")
    private String preferredCardPaymentMethod;
}
