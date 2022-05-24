package ru.instamart.api.model.v3;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public final class PromotionCodeV3 extends BaseObject {

    @JsonSchema(required = true)
    private String value;

    @JsonSchema(required = true)
    private String description;
}
