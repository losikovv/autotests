package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class AislesV2 extends BaseObject {
    private int id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private int productCount;
    private List<ProductV2> products;
}
