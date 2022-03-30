
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CurrencyV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("is_default")
    private Boolean isDefault;

    @JsonSchema(required = true)
    @JsonProperty("iso_code")
    private String isoCode;

    @JsonSchema(required = true)
    private String name;
}
