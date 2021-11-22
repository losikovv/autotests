
package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.FeatureFlagV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FeatureFlagsV2Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("feature_flags")
    private List<FeatureFlagV2> featureFlags;
}
