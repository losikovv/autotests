package ru.instamart.api.objects.shopper;

import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class DataSHP extends BaseObject {
    private String id;
    private String type;
    private AttributesSHP attributes;
}
