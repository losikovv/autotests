package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

public class Assembly extends BaseObject {

    private AssemblyData data;

    public AssemblyData getData() {
        return data;
    }

    public void setData(AssemblyData data) {
        this.data = data;
    }

}
