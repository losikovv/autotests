package ru.instamart.api.objects.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemsWeightDC extends BaseObject {
    private Integer min;
    private Integer max;
}
