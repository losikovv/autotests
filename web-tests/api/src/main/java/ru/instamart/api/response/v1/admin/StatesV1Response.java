
package ru.instamart.api.response.v1.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.StateV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StatesV1Response extends BaseResponseObject {

    @NotEmpty
    @JsonSchema(required = true)
    private List<StateV1> states;
}
