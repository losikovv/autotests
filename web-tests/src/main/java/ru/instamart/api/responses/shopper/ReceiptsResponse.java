package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.ReceiptsData;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReceiptsResponse extends BaseResponseObject {
    private ReceiptsData data;
}
