package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.ShipmentData;

import java.util.List;

public class ShipmentsResponse extends BaseResponseObject {

    private List<ShipmentData> data = null;

    public List<ShipmentData> getData() {
        return data;
    }

    public void setData(List<ShipmentData> data) {
        this.data = data;
    }

}
