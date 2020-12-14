package instamart.api.responses.v1;

import instamart.api.objects.v1.OperationalZone;
import instamart.api.responses.BaseResponseObject;

public class OperationalZoneResponse extends BaseResponseObject {

    private OperationalZone operational_zone;

    public OperationalZone getOperational_zone() {
        return operational_zone;
    }

    public void setOperational_zone(OperationalZone operational_zone) {
        this.operational_zone = operational_zone;
    }

}
