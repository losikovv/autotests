package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Store;

public class StoreResponse extends BaseResponseObject {

    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
