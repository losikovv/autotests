package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShipmentReviewV2 extends BaseObject {

	@JsonProperty("images")
	private List<ImageV2> images;

	@JsonProperty("rate")
	private Integer rate;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("issues")
	private List<IssueV2> issues;
}