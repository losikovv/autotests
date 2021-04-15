package ru.instamart.api.responses.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.delivery_club.ProductsDataDC;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDCResponse extends BaseResponseObject {
    private Integer totalCount;
    private ProductsDataDC data;
}
