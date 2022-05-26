
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class StateV1 extends BaseObject {

    @JsonSchema(required = true)
    private String abbr;

    @JsonSchema(required = true)
    @JsonProperty("country_id")
    private Long countryId;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;
}
