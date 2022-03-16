
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.DisablingFieldRulesV1;
import ru.instamart.api.model.v1.PromoTypeV1;
import ru.instamart.api.model.v1.ReasonV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class NewCompensationsV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("approve_threshold")
    private Long approveThreshold;

    @JsonSchema(required = true)
    @JsonProperty("customer_email")
    private String customerEmail;

    @JsonSchema(required = true)
    @JsonProperty("disabling_field_rules")
    private DisablingFieldRulesV1 disablingFieldRules;

    @JsonSchema(required = true)
    @JsonProperty("email_template")
    private String emailTemplate;

    @JsonSchema(required = true)
    @JsonProperty("promo_types")
    private List<PromoTypeV1> promoTypes;

    @JsonSchema(required = true)
    private List<ReasonV1> reasons;
}
