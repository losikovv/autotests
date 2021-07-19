package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;
import ru.instamart.api.model.v2.ServiceRateV2;

@Data
@EqualsAndHashCode(callSuper=false)
public class ServiceRateV2Response extends BaseObject {
	@JsonProperty("service_rate")
	private ServiceRateV2 serviceRate;
}