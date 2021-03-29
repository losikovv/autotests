package instamart.api.responses.deliveryclub;

import instamart.api.objects.delivery_club.ProductsDataDC;
import instamart.api.responses.BaseResponseObject;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDCResponse extends BaseResponseObject {
    public Integer totalCount;
    public ProductsDataDC data;
}
