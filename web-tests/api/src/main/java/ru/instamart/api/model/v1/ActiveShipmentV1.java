package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ActiveShipmentV1 extends BaseObject {

	@JsonProperty("store_id")
	private int storeId;

	private String number;

	private boolean delay;

	private RetailerV1 retailer;

	@JsonProperty("delivery_forecast")
	private DeliveryForecastV1 deliveryForecast;

	private MergeShipmentPossibilityV1 merge;

	@JsonProperty("delivery_window")
	private ActiveShipmentDeliveryWindowV1 deliveryWindow;

	private int id;

	private String state;

	private ActiveShipmentOrderV1 order;
}
