package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Taxon;

public class TaxonResponse extends BaseResponseObject {

    private Taxon taxon;

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

}
