
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MainNavigationV1 extends BaseObject {

    private List<ChildV1> children;

    @JsonSchema(required = true)
    private String id;

    @JsonSchema(required = true)
    private String path;

    @JsonSchema(required = true)
    private String title;
}
