package ru.instamart.api.model.v2.simple_recs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExtV2 extends BaseResponseObject {
	@JsonSchema(required = true)
	@JsonProperty("recommendation_type")
	private String recommendationType;

	@JsonSchema(required = true)
	@JsonProperty("recommendation_algorithm")
	private String recommendationAlgorithm;

	@JsonSchema(required = true)
	@JsonProperty("paid_recs_ids")
	private List<Object> paidRecsIds;
}