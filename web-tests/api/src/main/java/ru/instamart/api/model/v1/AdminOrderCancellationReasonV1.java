package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminOrderCancellationReasonV1 extends BaseObject {

    @JsonSchema(required = true)
    private String kind;

    @JsonSchema(required = true)
    private String description;

    @JsonSchema(required = true)
    private int id;
}