package instamart.api.v2.objects;

import instamart.api.common.BaseObject;

import java.util.List;
import java.util.StringJoiner;

public class Taxon extends BaseObject {

    private Integer id;
    private String type;
    private String name;
    private int products_count;
    private Icon icon;
    private Icon alt_icon;
    private List<Taxon> children = null;

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

    public int getProducts_count() {
        return products_count;
    }

    public void setProducts_count(int products_count) {
        this.products_count = products_count;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public Object getAlt_icon() {
        return alt_icon;
    }

    public void setAlt_icon(Icon alt_icon) {
        this.alt_icon = alt_icon;
    }

    public List<Taxon> getChildren() {
        return children;
    }

    public void setChildren(List<Taxon> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "\n- ", "")
                .add(name)
                .add("id: " + id)
                .add("type: " + type + "")
                .add("products_count: " + products_count);
        if (children != null) stringJoiner.add("\n" + name + " children: " + children);
        return stringJoiner.toString();
    }
}
