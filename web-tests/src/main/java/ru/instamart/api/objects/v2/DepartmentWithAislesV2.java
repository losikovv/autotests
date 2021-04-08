package ru.instamart.api.objects.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentWithAislesV2 extends BaseObject {
    private Integer id;
    private String type;
    private String name;
    @JsonProperty(value = "products_count")
    private Integer productsCount;
    private List<AisleV2> aisles = null;
}
