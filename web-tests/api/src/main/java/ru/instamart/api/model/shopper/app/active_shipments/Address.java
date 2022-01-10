package ru.instamart.api.model.shopper.app.active_shipments;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address{

	@JsonProperty("vatsPhone")
	private String vatsPhone;

	@JsonProperty("comments")
	private Object comments;

	@JsonProperty("deliveryToDoor")
	private Boolean deliveryToDoor;

	@JsonProperty("city")
	private String city;

	@JsonProperty("address1")
	private String address1;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("lon")
	private Double lon;

	@JsonProperty("building")
	private String building;

	@JsonProperty("doorPhone")
	private Object doorPhone;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("street")
	private String street;

	@JsonProperty("fullAddress")
	private String fullAddress;

	@JsonProperty("entrance")
	private Object entrance;

	@JsonProperty("floor")
	private Object floor;

	@JsonProperty("lat")
	private Double lat;
}