package ru.instamart.api.model.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class RetailerCustomSettingsSchemaItem extends BaseObject {

	@JsonProperty("type")
	@JsonSchema(required = true)
	private String type;

	@JsonProperty("key")
	@JsonSchema(required = true)
	private String key;

	@JsonProperty("required")
	@JsonSchema(required = true)
	private Boolean required;

	@JsonProperty("mask")
	@JsonSchema(required = true)
	private Boolean mask;
}