package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.StoreZoneV1;
import ru.instamart.api.response.BaseResponseObject;

@EqualsAndHashCode(callSuper = true)
@Data
public class StoreZoneV1Response extends BaseResponseObject {
    @JsonSchema(required = true)
    @JsonProperty(value = "zone")
    private StoreZoneV1 zone;
}