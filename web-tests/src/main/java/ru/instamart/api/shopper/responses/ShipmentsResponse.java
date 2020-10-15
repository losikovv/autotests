package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.ShipmentData;

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
