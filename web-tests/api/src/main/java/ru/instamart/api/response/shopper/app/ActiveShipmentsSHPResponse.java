package ru.instamart.api.response.shopper.app;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.shopper.app.active_shipments.DataItem;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class ActiveShipmentsSHPResponse extends BaseResponseObject {

	@JsonProperty("data")
	private List<DataItem> data;
}