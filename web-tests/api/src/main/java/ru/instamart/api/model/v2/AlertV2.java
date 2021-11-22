package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AlertV2 extends BaseObject {

    @JsonSchema(required = true)
    private String message;

    @JsonSchema(required = true)
    @JsonProperty(value = "full_message")
    private String fullMessage;

    @JsonSchema(required = true)
    private String type;

    @JsonSchema(required = true)
    private String key;

    @JsonSchema(required = true)
    private DetailsV2 details;
}
