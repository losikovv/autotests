package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonV2 extends BaseObject {
    private Integer id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private int productsCount;
    private IconV2 icon;
    @JsonProperty(value = "alt_icon")
    private IconV2 altIcon;
    private List<TaxonV2> children = null;
    @JsonProperty(value = "promo_services")
    private String[] promoServices = null;
    private List<RequirementV2> requirements = null;

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
