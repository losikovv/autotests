package ru.instamart.api.model.ris_exporter;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class CategoryRis extends BaseObject {

    @JsonSchema(required = true)
    private String id;
    @JsonSchema(required = true)
    private String name;
    private String parentId;
    @JsonSchema(required = true)
    private String imageUrl;
}

