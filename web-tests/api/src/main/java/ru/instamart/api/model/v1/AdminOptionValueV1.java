package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminOptionValueV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String presentation;

    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    @JsonProperty("option_type_id")
    private Integer optionTypeId;
}
