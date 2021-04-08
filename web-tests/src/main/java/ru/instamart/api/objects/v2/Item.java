package ru.instamart.api.objects.v2;

import ru.instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Item extends BaseObject {
    private Integer id;
    private String name;
    private String sku;
    private Product product;
    private Image image;
}
