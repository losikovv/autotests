package ru.instamart.api.model.shopper.admin;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import groovy.transform.EqualsAndHashCode;
import lombok.Data;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper = false)
public class OperationalZoneWorkflowSettingsV1 extends BaseObject {

    @JsonSchema(required = true)
    private Number increasingLateAssemblyPercentage;

    @JsonSchema(required = true)
    private Integer operationalZoneId;

    @JsonSchema(required = true)
    private Integer segmentAssemblyTardinessSec;

    @JsonSchema(required = true)
    private Integer serverOfferStorageTimeSec;

    @JsonSchema(required = true)
    private Integer timeToAcceptOfferSec;

    @JsonSchema(required = true)
    private Integer timeoutSegmentPassToClientSec;
}
