package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReferralProgram extends BaseObject {
    @JsonProperty(value = "ambassador_instacoins")
    private String ambassadorInstacoins;
    @JsonProperty(value = "referral_instacoins")
    private String referralInstacoins;
    @JsonProperty(value = "min_order_amount")
    private String minOrderAmount;
    @JsonProperty(value = "short_terms")
    private List<ShortTerm> shortTerms = null;
    private List<Link> links = null;
}
