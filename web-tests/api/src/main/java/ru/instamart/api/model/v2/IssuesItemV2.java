package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class IssuesItemV2 extends BaseObject {

	@JsonProperty("comment_needed")
	private Boolean commentNeeded;

	@JsonProperty("description")
	private String description;

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("position")
	private Integer position;
}