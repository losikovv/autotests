package ru.instamart.api.objects.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetAttributesSHP extends BaseObject {
    private String location;
    private Object qty;
    private String number;
    private String boxNumber;
}
