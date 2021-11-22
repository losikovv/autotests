package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReferralProgramV2 extends BaseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "ambassador_instacoins")
    private String ambassadorInstacoins;

    @JsonSchema(required = true)
    @JsonProperty(value = "referral_instacoins")
    private String referralInstacoins;

    @JsonSchema(required = true)
    @JsonProperty(value = "min_order_amount")
    private String minOrderAmount;

    @JsonSchema(required = true)
    @JsonProperty(value = "short_terms")
    private List<ShortTermV2> shortTerms = null;

    @JsonSchema(required = true)
    private List<LinkV2> links = null;
}
