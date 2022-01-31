package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ReviewableShipmentV2;

@EqualsAndHashCode(callSuper = true)
@Data
public  class ReviewableShipmentV2Response extends BaseObject {
	@JsonSchema(required = true)
	@JsonProperty("reviewable_shipment")
	private ReviewableShipmentV2 reviewableShipment;
}