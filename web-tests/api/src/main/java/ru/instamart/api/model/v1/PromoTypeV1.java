
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class PromoTypeV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @Null
    @JsonSchema(required = true)
    private Object label;

    @JsonSchema(required = true)
    private String name;
}
