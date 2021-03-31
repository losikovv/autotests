package instamart.api.responses.v2;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.v2.Facet;
import instamart.api.objects.v2.Meta;
import instamart.api.objects.v2.Product;
import instamart.api.objects.v2.Sort;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProductsResponse extends BaseResponseObject {
    private List<Product> products = null;
    private Meta meta;
    private List<Facet> facets = null;
    private List<Sort> sort = null;
}
