
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.AnonymousDeviceV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExternalAnalyticsV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("anonymous_device")
    private AnonymousDeviceV2 anonymousDevice;
}
