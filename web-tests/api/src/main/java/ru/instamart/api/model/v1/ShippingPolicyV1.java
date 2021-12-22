
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingPolicyV1 extends BaseObject {
    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    @JsonProperty("retailer_id")
    private Long retailerId;

    @JsonSchema(required = true)
    private List<RuleV1> rules;

    @JsonSchema(required = true)
    private String title;
}