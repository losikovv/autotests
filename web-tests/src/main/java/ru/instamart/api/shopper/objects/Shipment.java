package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class Shipment extends BaseObject {

    private ShipmentData data;

    public ShipmentData getData() {
        return data;
    }

    public void setData(ShipmentData data) {
        this.data = data;
    }

}
