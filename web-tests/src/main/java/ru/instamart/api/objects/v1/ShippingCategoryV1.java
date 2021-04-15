package ru.instamart.api.objects.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShippingCategoryV1 extends BaseObject {
    private String name;
    private String slug;
}

