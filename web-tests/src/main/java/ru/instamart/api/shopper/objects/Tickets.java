package instamart.api.shopper.objects;

import instamart.api.common.BaseObject;

import java.util.List;

public class Tickets extends BaseObject {

    private List<Data> data = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
