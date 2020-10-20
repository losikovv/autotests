package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Taxon;

import java.util.List;

public class TaxonsResponse extends BaseResponseObject {

    private List<Taxon> taxons = null;
    private List<Taxon> promoted_taxons = null;

    public List<Taxon> getTaxons() {
        return taxons;
    }

    public void setTaxons(List<Taxon> taxons) {
        this.taxons = taxons;
    }

    public List<Taxon> getPromoted_taxons() {
        return promoted_taxons;
    }

    public void setPromoted_taxons(List<Taxon> promoted_taxons) {
        this.promoted_taxons = promoted_taxons;
    }
}
