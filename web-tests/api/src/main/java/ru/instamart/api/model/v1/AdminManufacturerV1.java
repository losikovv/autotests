package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;


public class AdminManufacturerV1 extends BaseObject {

    @Null
    @JsonSchema(required = true)
    private Long id;

    @Null
    @JsonSchema(required = true)
    private String name;
}
