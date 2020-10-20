package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Facet;
import instamart.api.objects.v2.Meta;
import instamart.api.objects.v2.Product;
import instamart.api.objects.v2.Sort;

import java.util.List;

public class ProductsResponse extends BaseResponseObject {

    private List<Product> products = null;
    private Meta meta;
    private List<Facet> facets = null;
    private List<Sort> sort = null;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Facet> getFacets() {
        return facets;
    }

    public void setFacets(List<Facet> facets) {
        this.facets = facets;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

}
