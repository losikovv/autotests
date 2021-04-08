package ru.instamart.api.responses.v2;

import ru.instamart.api.responses.BaseResponseObject;
import ru.instamart.api.objects.v2.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductResponse extends BaseResponseObject {
    private Product product;
}
