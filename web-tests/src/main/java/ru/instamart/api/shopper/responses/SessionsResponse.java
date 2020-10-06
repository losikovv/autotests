package instamart.api.shopper.responses;


import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.Data;

public class SessionsResponse extends BaseResponseObject {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
