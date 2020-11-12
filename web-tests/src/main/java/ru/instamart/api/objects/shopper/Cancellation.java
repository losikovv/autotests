package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

public class Cancellation extends BaseObject {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
