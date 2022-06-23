package ru.instamart.api.model.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class SidebarReplacementPolicyV1 extends BaseObject {

    @JsonSchema(required = true)
    private int id;

    @JsonSchema(required = true)
    private String key;
}