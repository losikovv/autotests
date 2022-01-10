package ru.instamart.api.model.shopper.app.active_shipments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReplacementPolicy{

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private String description;

	@JsonProperty("type")
	private String type;
}