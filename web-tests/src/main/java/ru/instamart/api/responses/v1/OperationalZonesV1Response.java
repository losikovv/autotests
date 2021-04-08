package ru.instamart.api.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v1.OperationalZoneV1;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZonesV1Response extends BaseResponseObject {
    @JsonProperty(value = "operational_zones")
    private List<OperationalZoneV1> operationalZones = null;
}
