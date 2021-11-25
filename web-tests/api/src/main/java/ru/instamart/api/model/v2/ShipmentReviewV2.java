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
	@JsonProperty("images")
	private List<ImageV2> images;

	@JsonSchema(required = true)
	@JsonProperty("rate")
	private Integer rate;

	@Null
	@JsonSchema(required = true)
	@JsonProperty("comment")
	private String comment;

	@JsonSchema(required = true)
	@JsonProperty("id")
	private Integer id;

	@JsonSchema(required = true)
	@JsonProperty("issues")
	private List<IssueV2> issues;
}