
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppAnalyticsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("app_android_analytics_key")
    private String appAndroidAnalyticsKey;

    @JsonSchema(required = true)
    @JsonProperty("app_android_analytics_url")
    private String appAndroidAnalyticsUrl;

    @JsonSchema(required = true)
    @JsonProperty("app_ios_analytics_key")
    private String appIosAnalyticsKey;

    @JsonSchema(required = true)
    @JsonProperty("app_ios_analytics_url")
    private String appIosAnalyticsUrl;
}
