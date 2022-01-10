package ru.instamart.api.model.shopper.app.active_shipments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataItem{

	@JsonProperty("relationships")
	private Relationships relationships;

	@JsonProperty("attributes")
	private Attributes attributes;

	@JsonProperty("id")
	private String id;

	@JsonProperty("type")
	private String type;
}