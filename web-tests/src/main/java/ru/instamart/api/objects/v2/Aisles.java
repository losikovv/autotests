package instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;
import instamart.api.objects.BaseObject;

import java.util.List;

public final class Aisles extends BaseObject {

    private int id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private int productCount;
    private List<Product> products;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getProductCount() {
        return productCount;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aisles aisles = (Aisles) o;
        return id == aisles.id && productCount == aisles.productCount && Objects.equal(type, aisles.type) && Objects.equal(name, aisles.name) && Objects.equal(products, aisles.products);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, type, name, productCount, products);
    }

    @Override
    public String toString() {
        return "Aisles{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", productCount=" + productCount +
                ", products=" + products +
                '}';
    }
}
