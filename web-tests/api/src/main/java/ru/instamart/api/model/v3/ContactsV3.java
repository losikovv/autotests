
package ru.instamart.api.model.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ContactsV3 extends BaseObject {

    @JsonSchema(required = true)
    private String email;

    @JsonSchema(required = true)
    @JsonProperty("normalized_phone")
    private String normalizedPhone;

    @JsonSchema(required = true)
    private String phone;
}
