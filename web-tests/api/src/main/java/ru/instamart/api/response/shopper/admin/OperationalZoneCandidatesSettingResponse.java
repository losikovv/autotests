package ru.instamart.api.response.shopper.admin;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZoneCandidatesSettingResponse extends BaseResponseObject {

    @JsonProperty("surged_shift_threshold")
    @JsonSchema(required = true)
    private Integer surgedShiftThreshold;

    @JsonProperty("normal_shift_threshold")
    @JsonSchema(required = true)
    private Integer normalShiftThreshold;

    @JsonProperty("operational_zone_id")
    @JsonSchema(required = true)
    private Integer operationalZoneId;

}