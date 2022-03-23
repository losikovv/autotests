package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DepartmentV3 extends BaseObject {
    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String type;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String slug;

    @JsonSchema(required = true)
    @JsonProperty("products_count")
    private Integer productsCount;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("icon_url")
    private Object iconUrl;

    private List<ProductV3> products;

    private List<DepartmentV3> children;
}
