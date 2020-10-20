package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.ShipAddressChange;

public class ShipAddressChangeResponse extends BaseResponseObject {

    private ShipAddressChange ship_address_change;

    /**
     * No args constructor for use in serialization
     *
     */
    public ShipAddressChangeResponse() {
    }

    /**
     *
     * @param ship_address_change
     */
    public ShipAddressChangeResponse(ShipAddressChange ship_address_change) {
        super();
        this.ship_address_change = ship_address_change;
    }

    public ShipAddressChange getShip_address_change() {
        return ship_address_change;
    }

    public void setShip_address_change(ShipAddressChange ship_address_change) {
        this.ship_address_change = ship_address_change;
    }

}
