package ru.instamart.api.model.shopper.app.active_shipments;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Routes{

	@JsonProperty("data")
	private List<Object> data;
}