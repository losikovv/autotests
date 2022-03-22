
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.HeadV1;
import ru.instamart.api.model.v1.PageServerDataV1;
import ru.instamart.api.response.BaseResponseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class PageServerV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private PageServerDataV1 data;

    @JsonSchema(required = true)
    private HeadV1 head;
}
