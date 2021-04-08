package ru.instamart.api.responses.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.v2.StoreV2;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoreV2Response extends BaseResponseObject {
    private StoreV2 store;
}
