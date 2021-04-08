package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class Category extends BaseObject {
    private Integer id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private Integer productsCount;
    @JsonProperty(value = "promo_services")
    private String[] promoServices;
    private Integer position;
    private Icon icon;
    @JsonProperty(value = "alt_icon")
    private AltIcon altIcon;
    private List<Category> children;
    private List<Requirement> requirements;
}
