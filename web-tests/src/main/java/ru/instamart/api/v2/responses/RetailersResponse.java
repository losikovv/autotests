package instamart.api.v2.responses;

import instamart.api.v2.objects.Retailer;

import java.util.List;

public class RetailersResponse {

    private List<Retailer> retailers = null;

    public List<Retailer> getRetailers() {
        return retailers;
    }

    public void setRetailers(List<Retailer> retailers) {
        this.retailers = retailers;
    }

}
