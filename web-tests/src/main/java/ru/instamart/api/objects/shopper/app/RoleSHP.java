package ru.instamart.api.objects.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class RoleSHP extends BaseObject {
    private Integer id;
    private String name;
    private String humanName;
}
