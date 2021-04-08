package ru.instamart.api.responses.v2;

import ru.instamart.api.objects.v2.Item;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class FavoritesItemResponse extends BaseResponseObject {
    private Item item;
}
