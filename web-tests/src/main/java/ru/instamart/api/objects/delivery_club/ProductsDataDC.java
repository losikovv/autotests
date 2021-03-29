package instamart.api.objects.delivery_club;

import instamart.api.objects.BaseObject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDataDC extends BaseObject {
    public List<ProductDC> products;
}
