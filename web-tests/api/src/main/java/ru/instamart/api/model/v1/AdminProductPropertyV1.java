package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminProductPropertyV1 extends BaseObject {

    @JsonSchema(required = true)
    private Long id;

    @JsonSchema(required = true)
    private String value;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String presentation;

    @JsonSchema(required = true)
    @JsonProperty("product_id")
    private Long productId;

    @JsonSchema(required = true)
    private Integer position;
}
