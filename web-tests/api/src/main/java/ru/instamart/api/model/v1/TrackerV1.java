
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class TrackerV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("analytics_id")
    private String analyticsId;

    @JsonSchema(required = true)
    private String label;
}
