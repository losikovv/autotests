
package ru.instamart.api.model.workflows;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocationStart extends BaseObject {

    @JsonSchema(required = true)
    private List<Double> coordinates;

    @JsonSchema(required = true)
    private String type;
}
