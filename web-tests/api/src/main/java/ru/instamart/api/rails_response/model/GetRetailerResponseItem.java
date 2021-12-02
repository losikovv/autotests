package ru.instamart.api.rails_response.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetRetailerResponseItem extends BaseObject {

	@JsonProperty("retailer")
	private Retailer retailer;
}