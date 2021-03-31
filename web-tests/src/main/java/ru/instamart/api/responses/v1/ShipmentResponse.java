package instamart.api.responses.v1;

import instamart.api.objects.v1.Shipment;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentResponse extends BaseResponseObject {
    private Shipment shipment;
}
