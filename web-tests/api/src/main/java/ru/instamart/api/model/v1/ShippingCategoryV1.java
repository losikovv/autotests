package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingCategoryV1 extends BaseObject {
    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String slug;
}

