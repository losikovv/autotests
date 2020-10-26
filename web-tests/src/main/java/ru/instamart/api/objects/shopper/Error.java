package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class Error extends BaseObject {

    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
