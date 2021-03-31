package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDataDC extends BaseObject {
    public List<ProductDC> products;
}
