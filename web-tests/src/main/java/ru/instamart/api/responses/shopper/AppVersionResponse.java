package instamart.api.responses.shopper;

import instamart.api.objects.shopper.AppVersionData;
import instamart.api.responses.BaseResponseObject;

public class AppVersionResponse extends BaseResponseObject {

    private AppVersionData data;

    public AppVersionData getData() {
        return data;
    }

    public void setData(AppVersionData data) {
        this.data = data;
    }

}
