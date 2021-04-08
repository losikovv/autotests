package ru.instamart.api.responses.v2;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.Item;
import ru.instamart.api.objects.v2.Meta;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FavoritesListItemsResponse extends BaseResponseObject {
    private List<Item> items = null;
    private Meta meta;
}
