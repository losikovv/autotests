package ru.instamart.api.response.delivery_club;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.delivery_club.ProductsDataDC;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDCResponse extends BaseResponseObject {
    private Integer totalCount;
    private ProductsDataDC data;
}
