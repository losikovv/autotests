
package ru.instamart.api.response.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.ReplacementPolicyV3;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementPoliciesV3Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("replacement_policies")
    private List<ReplacementPolicyV3> replacementPolicies;
}
