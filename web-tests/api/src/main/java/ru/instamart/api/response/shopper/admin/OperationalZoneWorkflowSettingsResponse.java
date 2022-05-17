package ru.instamart.api.response.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.OperationalZoneWorkflowSettingsV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class OperationalZoneWorkflowSettingsResponse extends BaseResponseObject {

    @JsonProperty("operationalZoneWorkflowSetting")
    @JsonSchema(required = true)
    private OperationalZoneWorkflowSettingsV1 operationalZoneWorkflowSettings;
}
