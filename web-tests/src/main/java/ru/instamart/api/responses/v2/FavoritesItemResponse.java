package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.objects.v2.Item;
import instamart.api.responses.BaseResponseObject;

public final class FavoritesItemResponse extends BaseResponseObject {

    @JsonProperty(value = "item")
    private Item item;

    public Item getItem() {
        return item;
    }
}
