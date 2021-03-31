package instamart.api.responses.shopper;

import instamart.api.objects.shopper.ReceiptsData;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReceiptsResponse extends BaseResponseObject {
    private ReceiptsData data;
}
