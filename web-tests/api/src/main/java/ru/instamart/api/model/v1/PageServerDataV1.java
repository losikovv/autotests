
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageServerDataV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("preloaded_state")
    private PreloadedStateV1 preloadedState;
}
