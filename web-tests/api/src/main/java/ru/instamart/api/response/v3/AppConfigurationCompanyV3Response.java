package ru.instamart.api.response.v3;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v3.AppConfigurationCompanyV3;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppConfigurationCompanyV3Response extends BaseResponseObject {

    @JsonSchema(required = true)
    @JsonProperty("app_configuration_company")
    private AppConfigurationCompanyV3 appConfigurationCompany;
}
