
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminPreferenceV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("default_value")
    private Double defaultValue;

    @JsonSchema(required = true)
    @JsonProperty("human_name")
    private String humanName;

    @JsonSchema(required = true)
    private String kind;

    @JsonSchema(required = true)
    private String name;
}
