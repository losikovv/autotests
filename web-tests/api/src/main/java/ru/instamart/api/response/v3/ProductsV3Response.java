package ru.instamart.api.response.v3;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.FacetV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.SortV2;
import ru.instamart.api.model.v3.ProductDataV3;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsV3Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<ProductDataV3> products;
    @JsonSchema(required = true)
    private MetaV2 meta;
    @NotEmpty
    @JsonSchema(required = true)
    private List<FacetV2> facets;
    @JsonSchema(required = true)
    private List<SortV2> sort;
}
