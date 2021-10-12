package ru.instamart.api.model.v2.app_config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class AboutLinksV2 extends BaseObject {
    private Object faq;
    private Object rules;
    private Object terms;
    @JsonProperty(value = "personal_data_processing_policy")
    private Object personalDataProcessingPolicy;
    @JsonProperty(value = "sberprime_faq")
    private Object sberprimeFaq;
    @JsonProperty(value = "installment_plan_terms")
    private Object installmentPlanTerms;
}
