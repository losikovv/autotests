package instamart.api.objects.responses;

import instamart.api.objects.Store;

public class StoreResponse extends BaseResponseObject {

    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
