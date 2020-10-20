package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;

import java.util.List;

public class Items extends BaseObject {

    private List<Data> data = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
