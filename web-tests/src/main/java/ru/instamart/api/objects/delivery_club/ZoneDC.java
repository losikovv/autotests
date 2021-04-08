package ru.instamart.api.objects.delivery_club;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ZoneDC extends BaseObject {
    public String id;
}
