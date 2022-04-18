package ru.instamart.api.response.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.FeatureFlagV3;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FeatureFlagsV3Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty("feature_flags")
    private List<FeatureFlagV3> featureFlags;
}
