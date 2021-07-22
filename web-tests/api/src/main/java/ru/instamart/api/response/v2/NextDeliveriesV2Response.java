package ru.instamart.api.response.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.NextDeliveriesItemV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class NextDeliveriesV2Response extends BaseResponseObject {
	@JsonProperty("next_deliveries")
	private List<NextDeliveriesItemV2> nextDeliveries;
}