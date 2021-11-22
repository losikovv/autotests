package ru.instamart.api.model.delivery_club;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsDataDC extends BaseObject {
    @JsonSchema(required = true)
    private List<ProductDC> products;
}
