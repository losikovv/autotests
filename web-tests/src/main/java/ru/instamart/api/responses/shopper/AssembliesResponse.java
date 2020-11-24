package instamart.api.responses.shopper;

import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.shopper.AssemblyIncluded;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class AssembliesResponse extends BaseResponseObject {

    private List<AssemblyData> data = null;
    private List<AssemblyIncluded> included = null;

    public List<AssemblyData> getData() {
        return data;
    }

    public void setData(List<AssemblyData> data) {
        this.data = data;
    }

    public List<AssemblyIncluded> getIncluded() {
        return included;
    }

    public void setIncluded(List<AssemblyIncluded> included) {
        this.included = included;
    }

}
