package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper = false)
public class CreditCardV1 extends BaseObject {

    @JsonSchema(required = true)
    private int id;

    @JsonSchema(required = true)
    private String state;

    @JsonSchema(required = true)
    private boolean banned;

    @Null
    private String title;

    @JsonSchema(required = true)
    @JsonProperty("last_digits")
    private String lastDigits;
}