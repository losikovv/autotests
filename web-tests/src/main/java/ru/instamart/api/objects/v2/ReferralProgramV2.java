package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReferralProgramV2 extends BaseObject {
    @JsonProperty(value = "ambassador_instacoins")
    private String ambassadorInstacoins;
    @JsonProperty(value = "referral_instacoins")
    private String referralInstacoins;
    @JsonProperty(value = "min_order_amount")
    private String minOrderAmount;
    @JsonProperty(value = "short_terms")
    private List<ShortTermV2> shortTerms = null;
    private List<LinkV2> links = null;
}
