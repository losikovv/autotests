
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipAddressV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    private Object comments;

    @JsonSchema(required = true)
    @JsonProperty("full_address")
    private String fullAddress;

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String phone;

}
