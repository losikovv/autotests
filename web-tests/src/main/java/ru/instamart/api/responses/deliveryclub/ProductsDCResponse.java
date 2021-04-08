package ru.instamart.api.responses.deliveryclub;

import ru.instamart.api.objects.delivery_club.ProductsDataDC;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDCResponse extends BaseResponseObject {
    private Integer totalCount;
    private ProductsDataDC data;
}
