package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.ItemV2;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public final class FavoritesItemV2Response extends BaseResponseObject {
    private ItemV2 item;
}
