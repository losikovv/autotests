package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.EqualsAndHashCode;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class ItemReturn extends BaseObject {
    private Data data;
}
