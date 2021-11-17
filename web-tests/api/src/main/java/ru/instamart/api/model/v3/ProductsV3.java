package ru.instamart.api.model.v3;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.BaseObject;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsV3 extends BaseObject {
    @JsonProperty("products")
    private List<ProductDataV3> productDataV3;
    private List<CategoryV3> meta;
}
