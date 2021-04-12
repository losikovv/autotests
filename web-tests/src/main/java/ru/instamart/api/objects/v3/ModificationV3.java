package ru.instamart.api.objects.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ModificationV3 extends BaseObject {
    private Integer price;
    private Double quantity;
}
