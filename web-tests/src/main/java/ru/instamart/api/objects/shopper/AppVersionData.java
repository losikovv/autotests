package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AppVersionData extends BaseObject {
    private String id;
    private String type;
    private AppVersionAttributes attributes;
}
