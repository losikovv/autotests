package ru.instamart.api.model.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReasonSHP extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private Boolean active;

    @JsonSchema(required = true)
    private String created_at;

    @Null
    @JsonSchema(required = true)
    private String updated_at;

    @Null
    @JsonSchema(required = true)
    private Object position;
}
