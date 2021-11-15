package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ReplacementPolicyV2;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReplacementPoliciesV2Response extends BaseObject {

    @JsonProperty("replacement_policies")
    private List<ReplacementPolicyV2> replacementPolicies;
}