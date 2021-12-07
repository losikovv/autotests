package ru.instamart.api.response.shopper.app;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.AssemblyItemSHP;
import ru.instamart.api.model.shopper.app.AssemblySHP;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssemblySHPResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private AssemblySHP.Data data;

    @NotEmpty
    @JsonSchema(required = true)
    private List<AssemblyItemSHP.Data> included = null;
}
