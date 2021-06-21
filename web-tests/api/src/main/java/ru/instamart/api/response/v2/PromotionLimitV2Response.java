package ru.instamart.api.response.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.PromotionLimitsItemV2;

@EqualsAndHashCode(callSuper = true)
@Data
public class PromotionLimitV2Response  extends BaseObject {
	@JsonProperty(value = "promotion_limits")
	private List<PromotionLimitsItemV2> promotionLimits;
}