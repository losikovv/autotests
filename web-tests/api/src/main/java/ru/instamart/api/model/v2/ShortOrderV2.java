package ru.instamart.api.model.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShortOrderV2 extends BaseObject {

    @JsonSchema(required = true)
    private String number;

    @JsonSchema(required = true)
    private Double total;
}
