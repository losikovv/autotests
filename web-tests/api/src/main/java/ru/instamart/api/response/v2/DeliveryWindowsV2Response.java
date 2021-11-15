package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.DeliveryWindowV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowsV2Response extends BaseResponseObject {
	@JsonProperty("delivery_windows")
	private List<DeliveryWindowV2> deliveryWindows;
}