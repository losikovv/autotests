package instamart.api.shopper.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.AssemblyData;
import instamart.api.shopper.objects.AssemblyItemData;

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
