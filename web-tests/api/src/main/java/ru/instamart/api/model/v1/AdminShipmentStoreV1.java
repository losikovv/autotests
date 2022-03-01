
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import com.google.gson.annotations.Expose;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminShipmentStoreV1 extends BaseObject {

    @Expose
    private LocationV1 location;

    @JsonSchema(required = true)
    @JsonProperty("time_zone")
    private String timeZone;

    @JsonSchema(required = true)
    private String uuid;
}
