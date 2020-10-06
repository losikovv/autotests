package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

import java.util.List;

public class ReferralProgram extends BaseObject {

    private String ambassador_instacoins;
    private String referral_instacoins;
    private String min_order_amount;
    private List<ShortTerm> short_terms = null;
    private List<Link> links = null;

    public String getAmbassador_instacoins() {
        return ambassador_instacoins;
    }

    public void setAmbassador_instacoins(String ambassador_instacoins) {
        this.ambassador_instacoins = ambassador_instacoins;
    }

    public String getReferral_instacoins() {
        return referral_instacoins;
    }

    public void setReferral_instacoins(String referral_instacoins) {
        this.referral_instacoins = referral_instacoins;
    }

    public String getMin_order_amount() {
        return min_order_amount;
    }

    public void setMin_order_amount(String min_order_amount) {
        this.min_order_amount = min_order_amount;
    }

    public List<ShortTerm> getShort_terms() {
        return short_terms;
    }

    public void setShort_terms(List<ShortTerm> short_terms) {
        this.short_terms = short_terms;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
