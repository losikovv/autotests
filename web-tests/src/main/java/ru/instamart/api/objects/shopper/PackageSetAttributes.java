package instamart.api.objects.shopper;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetAttributes extends BaseObject {
    private String location;
    private Object qty;
    private String number;
    private String boxNumber;
}
