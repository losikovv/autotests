package instamart.api.responses.v1;

import instamart.api.objects.v1.OperationalZone;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class OperationalZonesResponse extends BaseResponseObject {

        private List<OperationalZone> operational_zones = null;

        public List<OperationalZone> getOperational_zones() {
            return operational_zones;
        }

        public void setOperational_zones(List<OperationalZone> operational_zones) {
            this.operational_zones = operational_zones;
        }

    }
