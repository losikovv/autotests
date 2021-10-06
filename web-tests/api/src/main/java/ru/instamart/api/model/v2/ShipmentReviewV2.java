package ru.instamart.api.model.v2;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShipmentReviewV2 extends BaseObject {

	@JsonProperty("images")
	private List<ImagesItemV2> images;

	@JsonProperty("rate")
	private Integer rate;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("issues")
	private List<IssuesItemV2> issues;
}