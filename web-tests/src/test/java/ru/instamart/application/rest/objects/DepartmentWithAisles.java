package ru.instamart.application.rest.objects;

import java.util.List;

public class DepartmentWithAisles extends BaseObject {

    private Integer id;
    private String type;
    private String name;
    private Integer products_count;
    private List<Aisle> aisles = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public DepartmentWithAisles() {
    }

    /**
     *
     * @param name
     * @param products_count
     * @param id
     * @param type
     * @param aisles
     */
    public DepartmentWithAisles(Integer id, String type, String name, Integer products_count, List<Aisle> aisles) {
        super();
        this.id = id;
        this.type = type;
        this.name = name;
        this.products_count = products_count;
        this.aisles = aisles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getProducts_count() {
        return products_count;
    }

    public void setProducts_count(Integer products_count) {
        this.products_count = products_count;
    }

    public List<Aisle> getAisles() {
        return aisles;
    }

    public void setAisles(List<Aisle> aisles) {
        this.aisles = aisles;
    }

}
