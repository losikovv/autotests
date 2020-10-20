package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Item;
import instamart.api.objects.v2.Meta;

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
