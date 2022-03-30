
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class FreeDeliveriesInfoV1 extends BaseObject {

    @JsonSchema(required = true)
    private Boolean limited;

    @JsonSchema(required = true)
    @JsonProperty("next_top_up_at")
    private String nextTopUpAt;

    @JsonSchema(required = true)
    private Integer remained;

    @JsonSchema(required = true)
    private Integer total;

    @JsonSchema(required = true)
    private Integer used;
}
