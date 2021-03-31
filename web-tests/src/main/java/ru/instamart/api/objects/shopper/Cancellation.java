package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class Cancellation extends BaseObject {
    private Data data;
}
