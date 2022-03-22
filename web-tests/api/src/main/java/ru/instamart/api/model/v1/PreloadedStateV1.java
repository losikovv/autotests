
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import javax.validation.constraints.Null;

@Data
@EqualsAndHashCode(callSuper=false)
public class PreloadedStateV1 extends BaseObject {

    @JsonSchema(required = true)
    private String action;

    @JsonSchema(required = true)
    private String controller;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("current_retailer")
    private Object currentRetailer;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("current_store")
    private Object currentStore;

    @JsonSchema(required = true)
    private String env;

    @JsonSchema(required = true)
    @JsonProperty("feature_configuration")
    private FeatureConfigurationV1 featureConfiguration;

    @JsonSchema(required = true)
    @JsonProperty("is_adult")
    private Boolean isAdult;

    @JsonSchema(required = true)
    @JsonProperty("is_mobile_device")
    private Boolean isMobileDevice;

    @JsonSchema(required = true)
    @JsonProperty("is_tablet_device")
    private Boolean isTabletDevice;

    @Null
    @JsonSchema(required = true)
    @JsonProperty("redirect_path")
    private Object redirectPath;

    @JsonSchema(required = true)
    @JsonProperty("view_data")
    private ViewDataV1 viewData;
}
