package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.PromotionLimitV2;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class PromotionLimitV2Response  extends BaseObject {
	@JsonProperty(value = "promotion_limits")
	private List<PromotionLimitV2> promotionLimits;
}