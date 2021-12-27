package ru.instamart.api.response.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.OperationalZoneDispatchSettingV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZoneDispatchSettingResponse extends BaseResponseObject {

	@JsonProperty("operationalZoneDispatchSetting")
	@JsonSchema(required = true)
	private OperationalZoneDispatchSettingV1 operationalZoneDispatchSetting;
}