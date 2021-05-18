package ru.instamart.api.model.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDataDC extends BaseObject {
    private List<ProductDC> products;
}
