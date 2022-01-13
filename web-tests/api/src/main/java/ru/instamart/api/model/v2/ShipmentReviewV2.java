package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShipmentReviewV2 extends BaseObject {

	@JsonSchema(required = true)
	private List<ImageV2> images;

	@JsonSchema(required = true)
	private Integer rate;

	@Null
	@JsonSchema(required = true)
	private String comment;

	@JsonSchema(required = true)
	private Integer id;

	@JsonSchema(required = true)
	private List<ReviewIssueV2> issues;

	@Null
	@JsonSchema(required = true)
	@JsonProperty("call_back")
	private Boolean callback;
}