package instamart.api.responses.deliveryclub;

import instamart.api.objects.delivery_club.ProductsDataDC;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDCResponse extends BaseResponseObject {
    public Integer totalCount;
    public ProductsDataDC data;
}
