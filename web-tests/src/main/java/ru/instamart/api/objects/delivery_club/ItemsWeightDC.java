package ru.instamart.api.objects.delivery_club;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemsWeightDC extends BaseObject {
    public Integer min;
    public Integer max;
}
