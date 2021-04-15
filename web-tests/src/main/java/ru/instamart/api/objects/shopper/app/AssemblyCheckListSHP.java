package ru.instamart.api.objects.shopper.app;

import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class AssemblyCheckListSHP extends BaseObject {
    private DataSHP data;
}
