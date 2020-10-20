package instamart.api.responses.v2;

import instamart.api.objects.v2.Retailer;

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
