package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoresV2Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<StoreV2> stores = null;

    @JsonSchema(required = true)
    private MetaV2 meta;

    @JsonSchema(required = true)
    @JsonProperty(value = "store_labels")
    private List<Object> storeLabels = null;
}
