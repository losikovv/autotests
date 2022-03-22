
package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PageBrowserHeadV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("csrf_param")
    private String csrfParam;

    @JsonSchema(required = true)
    @JsonProperty("csrf_token")
    private String csrfToken;
}
