package ru.instamart.api.objects.shopper;

import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class PackageSetsSHP extends BaseObject {
    private List<DataSHP> data = null;
}
