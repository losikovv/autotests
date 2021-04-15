package ru.instamart.api.objects.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class ItemsSHP extends BaseObject {
    private List<DataSHP> data = null;
}
