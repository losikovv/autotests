package ru.instamart.api.response.delivery_club;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.delivery_club.ProductsDataDC;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDCResponse extends BaseResponseObject {
    @JsonSchema(required = true)
    private Integer totalCount;
    @JsonSchema(required = true)
    private ProductsDataDC data;
}
