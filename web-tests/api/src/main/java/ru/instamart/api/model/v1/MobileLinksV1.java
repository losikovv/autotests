
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MobileLinksV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("link_faq")
    private String linkFaq;

    @JsonSchema(required = true)
    @JsonProperty("link_personal_data_processing_policy")
    private String linkPersonalDataProcessingPolicy;

    @JsonSchema(required = true)
    @JsonProperty("link_referral_rules")
    private Object linkReferralRules;

    @JsonSchema(required = true)
    @JsonProperty("link_review_policies")
    private Object linkReviewPolicies;

    @JsonSchema(required = true)
    @JsonProperty("link_rules")
    private String linkRules;

    @JsonSchema(required = true)
    @JsonProperty("link_sberprime_faq")
    private String linkSberprimeFaq;

    @JsonSchema(required = true)
    @JsonProperty("link_terms")
    private String linkTerms;
}
