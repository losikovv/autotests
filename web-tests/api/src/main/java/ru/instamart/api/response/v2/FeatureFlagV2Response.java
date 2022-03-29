package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.FeatureFlagV2;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeatureFlagV2Response extends BaseResponseObject {

	@JsonProperty("feature_flag")
	private FeatureFlagV2 featureFlag;
}