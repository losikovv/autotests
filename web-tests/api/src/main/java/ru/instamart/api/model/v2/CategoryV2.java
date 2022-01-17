package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class CategoryV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String type;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    @JsonProperty(value = "products_count")
    private Integer productsCount;

    @JsonSchema(required = true)
    @JsonProperty(value = "promo_services")
    private String[] promoServices;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private IconV2 icon;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "alt_icon")
    private AltIconV2 altIcon;

    private List<CategoryV2> children;

    @JsonSchema(required = true)
    private List<RequirementV2> requirements;

    @JsonSchema(required = true)
    private Integer depth;
}
