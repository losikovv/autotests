package ru.instamart.api.model.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZoneDispatchSettingV1 extends BaseObject {

	@JsonProperty("searchRadiusTransportBike")
	private Integer searchRadiusTransportBike;

	@JsonProperty("searchRadiusTransportAuto")
	private Integer searchRadiusTransportAuto;

	@JsonProperty("id")
	private Object id;

	@JsonProperty("operationalZoneId")
	private Integer operationalZoneId;

	@JsonProperty("searchRadiusTransportPedestrian")
	private Integer searchRadiusTransportPedestrian;

	@JsonProperty("searchRadiusManualRouting")
	private Integer searchRadiusManualRouting;
}