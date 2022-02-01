
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class PermissionV1 extends BaseObject {

    @NotEmpty
    @JsonSchema(required = true)
    private List<String> actions;

    @JsonSchema(required = true)
    private Boolean can;

    @JsonSchema(required = true)
    private Object conditions;

    @JsonSchema(required = true)
    private List<String> subjects;
}
