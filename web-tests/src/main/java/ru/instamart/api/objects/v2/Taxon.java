package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class Taxon extends BaseObject {
    private Integer id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private int productsCount;
    private Icon icon;
    @JsonProperty(value = "alt_icon")
    private Icon altIcon;
    private List<Taxon> children = null;
    @JsonProperty(value = "promo_services")
    private String[] promoServices = null;
    private List<Requirement> requirements = null;

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "\n- ", "")
                .add(name)
                .add("id: " + id)
                .add("type: " + type + "")
                .add("products_count: " + productsCount);
        if (children != null) stringJoiner.add("\n" + name + " children: " + children);
        return stringJoiner.toString();
    }
}
