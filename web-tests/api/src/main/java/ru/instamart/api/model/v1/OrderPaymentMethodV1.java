
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderPaymentMethodV1 extends BaseObject {

    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    @JsonProperty("display_admin_name")
    private String displayAdminName;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String type;
}
