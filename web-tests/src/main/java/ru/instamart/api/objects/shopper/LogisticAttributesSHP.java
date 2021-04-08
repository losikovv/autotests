package ru.instamart.api.objects.shopper;

import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class LogisticAttributesSHP extends BaseObject {
    private DataSHP data;
}
