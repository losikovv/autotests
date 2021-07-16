package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public @Data class RatesItemV2 {
	private Integer price;
	private String description;
	private String type;
	private String title;
	@JsonProperty("nominal_price")
	private Integer nominalPrice;
}