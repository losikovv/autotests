package ru.instamart.api.response.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.OperationalZoneV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZoneV1Response extends BaseResponseObject {
    @JsonProperty(value = "operational_zone")
    private OperationalZoneV1 operationalZone;
}
