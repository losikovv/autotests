
package ru.instamart.api.model.v3;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementPolicyV3 extends BaseObject {

    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    private Long id;
}
