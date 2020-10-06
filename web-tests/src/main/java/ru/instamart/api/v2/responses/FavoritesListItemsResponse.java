package instamart.api.v2.responses;

import instamart.api.common.BaseResponseObject;
import instamart.api.v2.objects.Item;
import instamart.api.v2.objects.Meta;

import java.util.List;

public class FavoritesListItemsResponse extends BaseResponseObject {

    private List<Item> items = null;
    private Meta meta;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
