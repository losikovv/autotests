
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ShippingPolicyV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingPoliciesV1Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    @JsonProperty("shipping_policies")
    private List<ShippingPolicyV1> shippingPolicies;
}