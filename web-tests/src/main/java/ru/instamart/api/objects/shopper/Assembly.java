package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class Assembly extends BaseObject {

    private AssemblyData data;

    public AssemblyData getData() {
        return data;
    }

    public void setData(AssemblyData data) {
        this.data = data;
    }

}
