package ru.instamart.api.response.v2;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;
import ru.instamart.api.model.v2.DeliveryWindowsItemV2;

@Data
@EqualsAndHashCode(callSuper=false)
public class DeliveryWindowsV2Response extends BaseResponseObject {
	@JsonProperty("delivery_windows")
	private List<DeliveryWindowsItemV2> deliveryWindows;
}