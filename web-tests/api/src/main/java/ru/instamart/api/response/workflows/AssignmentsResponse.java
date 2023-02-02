package ru.instamart.api.response.workflows;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.workflows.Stores;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class AssignmentsResponse extends BaseResponseObject{

	@JsonProperty("workflow_id")
	private String workflowId;

	@JsonProperty("distance")
	private String distance;

	@JsonProperty("stores")
	private List<Stores> stores;

	@JsonProperty("duration_to_store")
	private String durationToStore;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("pricing_bonus_cost")
	private String pricingBonusCost;

	@JsonProperty("distance_to_store")
	private String distanceToStore;

	@JsonProperty("delivery_count")
	private Integer deliveryCount;

	@JsonProperty("timeout")
	private String timeout;

	@JsonProperty("pricing_total_cost")
	private String pricingTotalCost;

	@JsonProperty("assembly_count")
	private Integer assemblyCount;

	@JsonProperty("duration")
	private String duration;

	@JsonProperty("shipments_count")
	private Integer shipmentsCount;

	@JsonProperty("pricing_base_cost")
	private String pricingBaseCost;

	@JsonProperty("performer_uuid")
	private String performerUuid;

	@JsonProperty("id")
	private String id;

	@JsonProperty("status")
	private String status;
}