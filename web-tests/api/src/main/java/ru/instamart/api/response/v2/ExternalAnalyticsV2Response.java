
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.AnonymousDeviceV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExternalAnalyticsV2Response extends BaseResponseObject {

    @JsonProperty("anonymous_device")
    private AnonymousDeviceV2 anonymousDevice;
}
