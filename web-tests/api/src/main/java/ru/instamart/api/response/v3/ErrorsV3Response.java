
package ru.instamart.api.response.v3;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.ErrorV3;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ErrorsV3Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private Integer code;
    @JsonSchema(required = true)
    private List<ErrorV3> errors;
}
