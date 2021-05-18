package ru.instamart.api.response.v2;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.v2.FacetV2;
import ru.instamart.api.model.v2.MetaV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.model.v2.SortV2;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsV2Response extends BaseResponseObject {
    private List<ProductV2> products = null;
    private MetaV2 meta;
    private List<FacetV2> facets = null;
    private List<SortV2> sort = null;
}
