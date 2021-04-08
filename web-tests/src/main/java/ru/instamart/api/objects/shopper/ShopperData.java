package ru.instamart.api.objects.shopper;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperData extends BaseObject {
    private String id;
    private String type;
    private ShopperAttributes attributes;
    private ShopperRelationships relationships;
}
