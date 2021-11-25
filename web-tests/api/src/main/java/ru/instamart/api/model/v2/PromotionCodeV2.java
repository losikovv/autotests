package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public final class PromotionCodeV2 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty(value = "value")
    private String code;

    @JsonSchema(required = true)
    private String description;
}
