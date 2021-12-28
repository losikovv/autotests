
package ru.instamart.api.response.v1;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v1.ProductV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductV1Response extends BaseResponseObject {

    @JsonSchema(required = true)
    private ProductV1 product;
    @JsonSchema(required = true)
    private List<Object> productProperties;
    @JsonSchema(required = true)
    private List<Object> productTaxons;
    @JsonSchema(required = true)
    private List<Object> promoBadges;

}
