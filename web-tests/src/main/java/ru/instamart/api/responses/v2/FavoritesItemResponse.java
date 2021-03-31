package instamart.api.responses.v2;

import instamart.api.objects.v2.Item;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class FavoritesItemResponse extends BaseResponseObject {
    private Item item;
}
