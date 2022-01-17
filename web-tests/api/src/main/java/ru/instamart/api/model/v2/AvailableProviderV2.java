
package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AvailableProviderV2 extends BaseObject {
    @JsonSchema(required = true)
    @JsonProperty("confirm_text")
    private String confirmText;

    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    @JsonProperty("icon_type")
    private String iconType;

    @JsonSchema(required = true)
    private String id;
}
