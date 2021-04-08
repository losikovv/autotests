package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperIncluded extends BaseObject {
    private String id;
    private String type;
    private ShopperIncludedAttributes attributes;
}
