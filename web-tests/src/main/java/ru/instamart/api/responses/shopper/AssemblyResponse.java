package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.shopper.AssemblyItemData;

import java.util.List;

public class AssemblyResponse extends BaseResponseObject {

    private AssemblyData data;
    private List<AssemblyItemData> included = null;

    public AssemblyData getData() {
        return data;
    }

    public void setData(AssemblyData data) {
        this.data = data;
    }

    public List<AssemblyItemData> getIncluded() {
        return included;
    }

    public void setIncluded(List<AssemblyItemData> included) {
        this.included = included;
    }
}
