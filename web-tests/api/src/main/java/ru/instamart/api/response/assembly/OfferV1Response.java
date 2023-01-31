package ru.instamart.api.response.assembly;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OfferV1Response{

	@JsonProperty("shipment_uuid")
	private String shipmentUuid;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("weight")
	private String weight;

	@JsonProperty("pricing_bonus_cost")
	private String pricingBonusCost;

	@JsonProperty("assembly_deadline")
	private String assemblyDeadline;

	@JsonProperty("pricing_total_cost")
	private String pricingTotalCost;

	@JsonProperty("timeout")
	private Integer timeout;

	@JsonProperty("items_count")
	private String itemsCount;

	@JsonProperty("is_first_order")
	private Boolean isFirstOrder;

	@JsonProperty("pricing_base_cost")
	private String pricingBaseCost;

	@JsonProperty("id")
	private String id;

	@JsonProperty("shipment_number")
	private String shipmentNumber;

	@JsonProperty("is_heavy")
	private Boolean isHeavy;

	@JsonProperty("thermal_bag_needed")
	private Boolean thermalBagNeeded;
}