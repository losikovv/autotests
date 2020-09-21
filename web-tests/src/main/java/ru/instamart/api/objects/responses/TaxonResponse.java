package instamart.api.objects.responses;

import instamart.api.objects.Taxon;

public class TaxonResponse extends BaseResponseObject {

    private Taxon taxon;

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

}
