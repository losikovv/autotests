package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.ItemV2;
import ru.instamart.api.objects.v2.MetaV2;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FavoritesListItemsV2Response extends BaseResponseObject {
    private List<ItemV2> items = null;
    private MetaV2 meta;
}
