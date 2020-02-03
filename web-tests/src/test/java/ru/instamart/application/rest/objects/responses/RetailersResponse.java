package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Retailer;

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
