package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.shopper.ShipmentData;

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
