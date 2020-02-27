package ru.instamart.application.rest.objects;

import java.util.List;

public class Department extends BaseObject {

    private Integer id;
    private Integer taxon_id;
    private String type;
    private String name;
    private int products_count;
    private List<Product> products = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Department() {
    }

    /**
     *
     * @param name
     * @param products_count
     * @param id
     * @param type
     * @param products
     */
    public Department(Integer id, Integer taxon_id, String type, String name, Integer products_count, List<Product> products) {
        super();
        this.id = id;
        this.taxon_id = taxon_id;
        this.type = type;
        this.name = name;
        this.products_count = products_count;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaxon_id() {
        return taxon_id;
    }

    public void setTaxon_id(Integer id) {
        this.taxon_id = taxon_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProducts_count() {
        return products_count;
    }

    public void setProducts_count(Integer products_count) {
        this.products_count = products_count;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}
