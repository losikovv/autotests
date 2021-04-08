package ru.instamart.api.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.instamart.api.objects.v1.OperationalZone;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZonesResponse extends BaseResponseObject {
    @JsonProperty(value = "operational_zones")
    private List<OperationalZone> operationalZones = null;
}
