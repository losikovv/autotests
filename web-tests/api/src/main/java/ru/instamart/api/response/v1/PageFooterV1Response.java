
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.PageFooterConfigV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageFooterV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private String component;

    @JsonSchema(required = true)
    private PageFooterConfigV1 config;
}
