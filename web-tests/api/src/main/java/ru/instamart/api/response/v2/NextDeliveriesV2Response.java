package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.NextDeliveriesV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class NextDeliveriesV2Response extends BaseResponseObject {
	@JsonProperty("next_deliveries")
	private List<NextDeliveriesV2> nextDeliveries;
}