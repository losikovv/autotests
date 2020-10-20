package instamart.api.objects.v2;

import instamart.api.objects.BaseObject;

public class Aisle extends BaseObject {

    private Integer id;
    private Integer taxon_id;
    private String type;
    private String name;
    private Integer products_count;
    private Icon icon;

    /**
     * No args constructor for use in serialization
     *
     */
    public Aisle() {
    }

    /**
     *
     * @param name
     * @param icon
     * @param taxon_id
     * @param products_count
     * @param id
     * @param type
     */
    public Aisle(Integer id, Integer taxon_id, String type, String name, Integer products_count, Icon icon) {
        super();
        this.id = id;
        this.taxon_id = taxon_id;
        this.type = type;
        this.name = name;
        this.products_count = products_count;
        this.icon = icon;
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

    public void setTaxon_id(Integer taxon_id) {
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

    public Integer getProducts_count() {
        return products_count;
    }

    public void setProducts_count(Integer products_count) {
        this.products_count = products_count;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

}
