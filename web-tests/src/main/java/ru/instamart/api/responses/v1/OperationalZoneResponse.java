package instamart.api.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.v1.OperationalZone;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationalZoneResponse extends BaseResponseObject {
    @JsonProperty(value = "operational_zone")
    private OperationalZone operationalZone;
}
