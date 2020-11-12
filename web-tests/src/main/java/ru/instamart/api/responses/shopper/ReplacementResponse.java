package instamart.api.responses.shopper;

import instamart.api.objects.shopper.ReplacementData;
import instamart.api.responses.BaseResponseObject;

public class ReplacementResponse extends BaseResponseObject {

    private ReplacementData data;

    public ReplacementData getData() {
        return data;
    }

    public void setData(ReplacementData data) {
        this.data = data;
    }

}
