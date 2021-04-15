package ru.instamart.api.objects.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDataDC extends BaseObject {
    private List<ProductDC> products;
}
