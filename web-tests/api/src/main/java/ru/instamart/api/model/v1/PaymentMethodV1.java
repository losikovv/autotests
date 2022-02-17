
package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PaymentMethodV1 extends BaseObject {

    private String environment;
    @JsonSchema(required = true)
    private Long id;
    @JsonSchema(required = true)
    private String key;
    @JsonSchema(required = true)
    private String name;
}
