package ru.instamart.api.model.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ScangoEnginesItem extends BaseObject {

    @JsonProperty("codeType")
    @JsonSchema(required = true)
    private String codeType;

    @JsonProperty("processType")
    @JsonSchema(required = true)
    private String processType;

    @JsonProperty("key")
    @JsonSchema(required = true)
    private String key;

    @JsonProperty("retailerCustomSettingsSchema")
    @JsonSchema(required = true)
    private List<RetailerCustomSettingsSchemaItem> retailerCustomSettingsSchema;
}