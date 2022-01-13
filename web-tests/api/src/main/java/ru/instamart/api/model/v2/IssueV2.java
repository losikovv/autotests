package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class IssueV2 extends BaseObject {

	@JsonSchema(required = true)
	@JsonProperty("comment_needed")
	private Boolean commentNeeded;

	@JsonSchema(required = true)
	@JsonProperty("description")
	private String description;

	@JsonSchema(required = true)
	@JsonProperty("id")
	private Integer id;
}