package ru.instamart.api.model.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemV2 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;
    @JsonSchema(required = true)
    private String name;
    @JsonSchema(required = true)
    private String sku;
    @JsonSchema(ignore = true)
    private ProductV2 product;
    @JsonSchema(required = true)
    private ImageV2 image;
}
