package ru.instamart.api.response.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.app_config.AppConfigurationV2;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppConfigurationV2Response extends BaseResponseObject {
    @JsonProperty(value = "app_configuration")
    private AppConfigurationV2 appConfiguration;
}
