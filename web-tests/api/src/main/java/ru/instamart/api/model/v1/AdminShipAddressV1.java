package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminShipAddressV1 extends BaseObject {

    @JsonSchema(required = true)
    private String city;

    @JsonSchema(required = true)
    private String fullname;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonSchema(required = true)
    private String address;
}
