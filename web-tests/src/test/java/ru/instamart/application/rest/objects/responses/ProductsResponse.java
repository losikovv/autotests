package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Facet;
import ru.instamart.application.rest.objects.Meta;
import ru.instamart.application.rest.objects.Product;
import ru.instamart.application.rest.objects.Sort;

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
