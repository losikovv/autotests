package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.StoresV1;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoresV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    private List<StoresV1> stores;
    @JsonSchema(required = true)
    private MetaV2 meta;
}
