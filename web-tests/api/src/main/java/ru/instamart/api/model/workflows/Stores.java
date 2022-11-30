package ru.instamart.api.model.workflows;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class Stores extends BaseObject {

	@JsonProperty("store_name")
	private String storeName;

	@JsonProperty("store_address")
	private String storeAddress;

	@JsonProperty("shipments")
	private List<Shipments> shipments;
}