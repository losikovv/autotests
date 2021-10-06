package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class CategoryV3 extends BaseObject {
    private Long id;
    @JsonProperty("parent_id")
    private Long parentId;
    private String type;
    private String name;
    private String slug;
    @JsonProperty("products_count")
    private Integer productsCount;
    @JsonProperty("promo_services")
    private List<Object> promoServices;
    @JsonProperty("icon_url")
    private String iconUrl;
    @JsonProperty("canonical_url")
    private String canonicalUrl;
    @JsonProperty("alt_icon")
    private String altIcon;
    private List<CategoryV3> children;
    private List<Object> requirements;
}
