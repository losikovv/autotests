package ru.instamart.api.model.v2;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopPhrasesItemV2 {

	@JsonProperty("name")
	private String name;

	@JsonProperty("icon")
	private IconV2 icon;
}