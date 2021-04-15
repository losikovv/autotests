package ru.instamart.api.objects.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class ItemReturnSHP extends BaseObject {
    private DataSHP data;
}
