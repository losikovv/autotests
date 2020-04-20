package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Taxon;

import java.util.List;

public class TaxonsResponse extends BaseResponseObject {

    private List<Taxon> taxons = null;

    public List<Taxon> getTaxons() {
        return taxons;
    }

    public void setTaxons(List<Taxon> taxons) {
        this.taxons = taxons;
    }

}
