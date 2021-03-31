package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.ShipmentData;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShipmentResponse extends BaseResponseObject {
    private ShipmentData data;
}
