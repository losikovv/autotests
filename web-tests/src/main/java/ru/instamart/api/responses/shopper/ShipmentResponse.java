package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.ShipmentData;

public class ShipmentResponse extends BaseResponseObject {

    private ShipmentData data;

    public ShipmentData getData() {
        return data;
    }

    public void setData(ShipmentData data) {
        this.data = data;
    }

}
