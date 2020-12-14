package instamart.api.responses.v1;

import instamart.api.objects.v1.Shipment;
import instamart.api.responses.BaseResponseObject;

public class ShipmentResponse extends BaseResponseObject {

    private Shipment shipment;

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

}
