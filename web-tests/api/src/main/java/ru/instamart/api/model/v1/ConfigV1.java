
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ConfigV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    @JsonProperty("additional_seconds_for_assembly")
    private Object additionalSecondsForAssembly;

    @JsonSchema(required = true)
    @JsonProperty("import_key_postfix")
    private String importKeyPostfix;

    @JsonSchema(required = true)
    @JsonProperty("lifepay_identifier")
    private String lifepayIdentifier;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("seconds_for_assembly_item")
    private Object secondsForAssemblyItem;
}
