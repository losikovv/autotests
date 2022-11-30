package ru.instamart.api.model.workflows;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class Shipments extends BaseObject {

	@JsonProperty("items_total")
	private String itemsTotal;

	@JsonProperty("items_count")
	private String itemsCount;

	@JsonProperty("is_assembly")
	private Boolean isAssembly;

	@JsonProperty("is_first_order")
	private Boolean isFirstOrder;

	@JsonProperty("weight")
	private String weight;

	@JsonProperty("is_delivery")
	private Boolean isDelivery;

	@JsonProperty("shipment_number")
	private String shipmentNumber;

	@JsonProperty("is_heavy")
	private Boolean isHeavy;

	@JsonProperty("thermal_bag_needed")
	private Boolean thermalBagNeeded;
}