package ru.instamart.api.model.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
public class ZoneV2 extends BaseObject {
    @JsonSchema(required = true)
    private Double lon;
    @JsonSchema(required = true)
    private Double lat;

    @Override
    public String toString() {
        return "lat: " + lat + ", lon: " + lon;
    }
}
