package instamart.api.responses.shopper;

import instamart.api.objects.shopper.ReplacementData;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ReplacementResponse extends BaseResponseObject {
    private ReplacementData data;
}
