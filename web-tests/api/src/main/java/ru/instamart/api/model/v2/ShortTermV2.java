package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShortTermV2 extends BaseObject {
    @Null
    @JsonSchema(required = true)
    private String emoji;

    @Null
    @JsonSchema(required = true)
    @JsonProperty(value = "icon_url")
    private String iconUrl;

    @JsonSchema(required = true)
    private String html;
}
