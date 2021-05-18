package ru.instamart.api.model.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemV2 extends BaseObject {
    private Integer id;
    private String name;
    private String sku;
    private ProductV2 product;
    private ImageV2 image;
}
