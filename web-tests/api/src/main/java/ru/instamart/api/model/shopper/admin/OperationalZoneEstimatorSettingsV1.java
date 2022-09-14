package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class OperationalZoneEstimatorSettingsV1 extends BaseObject {
    @JsonSchema(required = true)
    private Integer operationalZoneId;

    @JsonSchema(required = true)
    private Number correctionFactor;

    @JsonSchema(required = true)
    private Integer increasingFactor;

    @JsonSchema(required = true)
    private Integer minimumSegmentLength;
}
