package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.EqualsAndHashCode;

import java.util.List;

@lombok.Data
@EqualsAndHashCode(callSuper=false)
public class Roles extends BaseObject {
    private List<Data> data = null;
}
