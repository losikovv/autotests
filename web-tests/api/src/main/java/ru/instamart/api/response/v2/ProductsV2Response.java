package ru.instamart.api.response.v2;

import com.github.imifou.jsonschema.module.addon.annotation.JsonSchema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.FacetV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.model.v2.SortV2;
import ru.instamart.api.response.BaseResponseObject;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsV2Response extends BaseResponseObject {
    @NotEmpty
    @JsonSchema(required = true)
    private List<ProductV2> products = null;
    @JsonSchema(required = true)
    private MetaV2 meta;
    @NotEmpty
    @JsonSchema(required = true)
    private List<FacetV2> facets = null;
    @JsonSchema(required = true)
    private List<SortV2> sort = null;
}
