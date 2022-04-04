
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionsV1 extends BaseObject {

    @JsonSchema(required = true)
    @JsonProperty("app_android_min_version")
    private String appAndroidMinVersion;

    @JsonSchema(required = true)
    @JsonProperty("app_android_recommended_version")
    private String appAndroidRecommendedVersion;

    @JsonSchema(required = true)
    @JsonProperty("app_ios_min_version")
    private String appIosMinVersion;

    @JsonSchema(required = true)
    @JsonProperty("app_ios_recommended_version")
    private String appIosRecommendedVersion;
}
