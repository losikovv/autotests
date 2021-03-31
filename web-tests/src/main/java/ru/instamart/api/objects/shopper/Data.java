package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class Data extends BaseObject {
    private String id;
    private String type;
    private Attributes attributes;
}
