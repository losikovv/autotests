package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.EqualsAndHashCode;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class Tickets extends BaseObject {
    private List<Data> data = null;
}
