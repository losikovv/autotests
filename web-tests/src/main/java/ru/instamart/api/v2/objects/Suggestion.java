package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

import java.util.List;

public class Suggestion extends BaseObject {

    private List<String> searches = null;
    private List<Taxon> taxons = null;
    private List<Product> products = null;
    private List<SearchPhrase> search_phrases = null;

    public List<String> getSearches() {
        return searches;
    }

    public void setSearches(List<String> searches) {
        this.searches = searches;
    }

    public List<Taxon> getTaxons() {
        return taxons;
    }

    public void setTaxons(List<Taxon> taxons) {
        this.taxons = taxons;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<SearchPhrase> getSearch_phrases() {
        return search_phrases;
    }

    public void setSearch_phrases(List<SearchPhrase> search_phrases) {
        this.search_phrases = search_phrases;
    }

}
