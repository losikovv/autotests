package instamart.api.responses.shopper;


import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.SessionData;

public class SessionsResponse extends BaseResponseObject {

    private SessionData data;

    public SessionData getData() {
        return data;
    }

    public void setData(SessionData data) {
        this.data = data;
    }

}
