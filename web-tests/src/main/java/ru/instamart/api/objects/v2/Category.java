package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import instamart.api.objects.BaseObject;

import java.util.List;

public final class Category extends BaseObject {

    private Integer id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private Integer productsCount;
    @JsonProperty(value = "promo_services")
    private List<PromoService> promoServices;
    private Integer position;
    private Icon icon;
    @JsonProperty(value = "alt_icon")
    private String altIcon;
    private List<Category> children;
    private List<Requirement> requirements;

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getProductsCount() {
        return productsCount;
    }

    public List<PromoService> getPromoServices() {
        return promoServices;
    }

    public Integer getPosition() {
        return position;
    }

    public Icon getIcon() {
        return icon;
    }

    public String getAltIcon() {
        return altIcon;
    }

    public List<Category> getChildren() {
        return children;
    }

    public List<Requirement> getRequirements() {
        return requirements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equal(id, category.id) && Objects.equal(type, category.type) && Objects.equal(name, category.name) && Objects.equal(productsCount, category.productsCount) && Objects.equal(promoServices, category.promoServices) && Objects.equal(position, category.position) && Objects.equal(icon, category.icon) && Objects.equal(altIcon, category.altIcon) && Objects.equal(children, category.children) && Objects.equal(requirements, category.requirements);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type, name, productsCount, promoServices, position, icon, altIcon, children, requirements);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", productsCount=" + productsCount +
                ", promoServices=" + promoServices +
                ", position=" + position +
                ", icon=" + icon +
                ", altIcon='" + altIcon + '\'' +
                ", children=" + children +
                ", requirements=" + requirements +
                '}';
    }
}
