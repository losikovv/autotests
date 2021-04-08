package ru.instamart.api.objects.shopper;

import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class CancellationSHP extends BaseObject {
    private DataSHP data;
}
