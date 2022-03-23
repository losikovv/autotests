package ru.instamart.api.model.v3;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsV3 extends BaseObject {
    private List<ProductV3> products;
    private List<CategoryV3> meta;
}
