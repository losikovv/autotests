package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;
import java.util.StringJoiner;

@Data
@EqualsAndHashCode(callSuper=false)
public class TaxonV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    private String type;

    @JsonSchema(required = true)
    private String name;

    @JsonProperty(value = "products_count")
    private int productsCount;

    @JsonSchema(required = true)
    private IconV2 icon;

    @Null
    @JsonProperty(value = "alt_icon")
    private IconV2 altIcon;

    private List<TaxonV2> children = null;

    @JsonProperty(value = "promo_services")
    private String[] promoServices = null;

    @JsonSchema(required = true)
    private List<RequirementV2> requirements = null;

    private Integer position;

    private Integer depth;

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
