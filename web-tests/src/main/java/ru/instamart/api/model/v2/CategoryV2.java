package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class CategoryV2 extends BaseObject {
    private Integer id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private Integer productsCount;
    @JsonProperty(value = "promo_services")
    private String[] promoServices;
    private Integer position;
    private IconV2 icon;
    @JsonProperty(value = "alt_icon")
    private AltIconV2 altIcon;
    private List<CategoryV2> children;
    private List<RequirementV2> requirements;
}
