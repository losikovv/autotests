package instamart.api.responses.shopper;

import instamart.api.objects.shopper.Prereplacement;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PrereplacementsResponse extends BaseResponseObject {
    private Prereplacement prereplacement;
}
