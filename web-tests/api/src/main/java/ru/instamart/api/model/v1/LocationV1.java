
package ru.instamart.api.model.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class LocationV1 extends BaseObject {

    @JsonSchema(required = true)
    private String building;

    @JsonSchema(required = true)
    private String city;

    @JsonSchema(required = true)
    @JsonProperty("store_location")
    private AdminShipmentStoreLocationV1 storeLocation;

    @JsonSchema(required = true)
    private String street;
}
