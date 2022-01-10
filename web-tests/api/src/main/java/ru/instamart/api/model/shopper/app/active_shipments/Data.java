package ru.instamart.api.model.shopper.app.active_shipments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Data{

	@JsonProperty("id")
	private String id;

	@JsonProperty("type")
	private String type;
}