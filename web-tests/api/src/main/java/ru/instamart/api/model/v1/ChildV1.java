
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ChildV1 extends BaseObject {

    @JsonSchema(required = true)
    private String id;
    @JsonSchema(required = true)
    private String path;
    @JsonProperty("suburl_prefixes")
    private List<String> suburlPrefixes;
    @JsonSchema(required = true)
    private String title;
}
