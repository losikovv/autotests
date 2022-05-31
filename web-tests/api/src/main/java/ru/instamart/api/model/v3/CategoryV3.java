package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.AltIconV2;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CategoryV3 extends BaseObject {
    @JsonSchema(required = true)
    private Long id;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("parent_id")
    private Long parentId;

    @JsonSchema(required = true)
    private String type;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String slug;

    @JsonSchema(required = true)
    @JsonProperty("products_count")
    private Integer productsCount;

    @JsonSchema(required = true)
    @JsonProperty("promo_services")
    private List<Object> promoServices;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("icon_url")
    private String iconUrl;

    @JsonSchema(required = true)
    @JsonProperty("canonical_url")
    private String canonicalUrl;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("alt_icon")
    private AltIconV2 altIcon;

    private List<CategoryV3> children;

    @JsonSchema(required = true)
    private List<Object> requirements;
}
