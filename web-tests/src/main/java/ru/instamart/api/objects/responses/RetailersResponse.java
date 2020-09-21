package instamart.api.objects.responses;

import instamart.api.objects.Retailer;

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
