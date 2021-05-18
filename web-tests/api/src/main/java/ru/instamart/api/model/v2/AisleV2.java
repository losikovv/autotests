package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AisleV2 extends BaseObject {
    private Integer id;
    @JsonProperty(value = "taxon_id")
    private Integer taxonId;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private Integer productsCount;
    private List<ProductV2> products;
    private IconV2 icon;
}
