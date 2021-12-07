package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailerV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer id;

    @JsonSchema(required = true)
    private String name;

    @JsonSchema(required = true)
    private String slug;

    @Null
    @JsonSchema(required = true)
    private Integer position;

    @JsonSchema(required = true)
    private Boolean available;

    @Null
    @JsonSchema(required = true)
    private String logoUrl;

    @Null
    @JsonSchema(required = true)
    private String logoBackgroundColor;
}
