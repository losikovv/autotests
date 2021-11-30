package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ShortOrderV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class MergeLineItemsV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private ShortOrderV2 order;
}
