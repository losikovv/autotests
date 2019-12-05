package ru.instamart.application.rest.objects.responses;

import ru.instamart.application.rest.objects.Meta;
import ru.instamart.application.rest.objects.Store;

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
