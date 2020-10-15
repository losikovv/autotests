package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.AssemblyData;
import instamart.api.shopper.objects.ShipmentData;

import java.util.List;

public class AssembliesResponse extends BaseResponseObject {

    private List<AssemblyData> data = null;
    private List<ShipmentData> included = null;

    public List<AssemblyData> getData() {
        return data;
    }

    public void setData(List<AssemblyData> data) {
        this.data = data;
    }

    public List<ShipmentData> getIncluded() {
        return included;
    }

    public void setIncluded(List<ShipmentData> included) {
        this.included = included;
    }

}
