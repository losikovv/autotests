package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Taxon;

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
