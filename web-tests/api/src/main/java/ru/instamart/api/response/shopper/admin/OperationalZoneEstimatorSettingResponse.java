package ru.instamart.api.response.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.OperationalZoneEstimatorSettingsV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class OperationalZoneEstimatorSettingResponse extends BaseResponseObject {

    @JsonProperty("operationalZoneEstimatorSetting")
    @JsonSchema(required = true)
    private OperationalZoneEstimatorSettingsV1 operationalZoneEstimatorSettings;
}
