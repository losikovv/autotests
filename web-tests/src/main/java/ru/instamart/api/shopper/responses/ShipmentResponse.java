package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.ShipmentData;

public class ShipmentResponse extends BaseResponseObject {

    private ShipmentData data;

    public ShipmentData getData() {
        return data;
    }

    public void setData(ShipmentData data) {
        this.data = data;
    }

}
