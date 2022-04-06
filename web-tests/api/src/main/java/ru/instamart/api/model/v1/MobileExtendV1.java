
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class MobileExtendV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @Null
    private String json;

    @Null
    @JsonSchema(required = true)
    private String prop;
}
