package instamart.api.shopper.responses;


import instamart.api.common.BaseResponseObject;
import instamart.api.shopper.objects.SessionData;

public class SessionsResponse extends BaseResponseObject {

    private SessionData data;

    public SessionData getData() {
        return data;
    }

    public void setData(SessionData data) {
        this.data = data;
    }

}
