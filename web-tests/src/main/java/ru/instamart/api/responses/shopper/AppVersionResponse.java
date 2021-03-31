package instamart.api.responses.shopper;

import instamart.api.objects.shopper.AppVersionData;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionResponse extends BaseResponseObject {
    private AppVersionData data;
}
