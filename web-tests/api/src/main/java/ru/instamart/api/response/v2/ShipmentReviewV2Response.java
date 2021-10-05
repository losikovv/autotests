package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ShipmentReviewV2;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShipmentReviewV2Response extends BaseObject {

	@JsonProperty("shipment_review")
	private ShipmentReviewV2 shipmentReview;
}