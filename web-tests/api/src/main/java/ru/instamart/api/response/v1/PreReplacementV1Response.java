
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.PreReplacementV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PreReplacementV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private PreReplacementV1 prereplacement;
}
