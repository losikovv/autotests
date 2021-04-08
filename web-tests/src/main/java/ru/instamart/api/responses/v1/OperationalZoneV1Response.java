package ru.instamart.api.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v1.OperationalZoneV1;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZoneV1Response extends BaseResponseObject {
    @JsonProperty(value = "operational_zone")
    private OperationalZoneV1 operationalZone;
}
