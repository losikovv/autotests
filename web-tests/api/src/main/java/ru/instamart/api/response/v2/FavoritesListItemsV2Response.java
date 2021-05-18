package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.ItemV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class FavoritesListItemsV2Response extends BaseResponseObject {
    private List<ItemV2> items = null;
    private MetaV2 meta;
}
