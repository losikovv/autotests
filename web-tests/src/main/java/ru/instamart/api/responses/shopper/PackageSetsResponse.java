package instamart.api.responses.shopper;

import instamart.api.objects.shopper.PackageSetData;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public class PackageSetsResponse extends BaseResponseObject {

    private List<PackageSetData> data = null;

    public List<PackageSetData> getData() {
        return data;
    }

    public void setData(List<PackageSetData> data) {
        this.data = data;
    }

}
