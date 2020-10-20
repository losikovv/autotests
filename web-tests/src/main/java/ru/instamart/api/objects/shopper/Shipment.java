package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class Shipment extends BaseObject {

    private ShipmentData data;

    public ShipmentData getData() {
        return data;
    }

    public void setData(ShipmentData data) {
        this.data = data;
    }

}
