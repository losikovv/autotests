package ru.instamart.api.response.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.StoreV1;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoresSHPResponse extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<StoreV1> stores = null;

    @JsonSchema(required = true)
    private MetaV1 meta;
}
