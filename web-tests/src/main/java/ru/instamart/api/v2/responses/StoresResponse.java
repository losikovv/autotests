package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Meta;
import instamart.api.v2.objects.Store;

import java.util.List;

public class StoresResponse extends BaseResponseObject {

    private List<Store> stores = null;
    private Meta meta;

    /**
     * No args constructor for use in serialization
     *
     */
    public StoresResponse() {
    }

    /**
     *
     * @param stores
     * @param meta
     */
    public StoresResponse(List<Store> stores, Meta meta) {
        super();
        this.stores = stores;
        this.meta = meta;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
