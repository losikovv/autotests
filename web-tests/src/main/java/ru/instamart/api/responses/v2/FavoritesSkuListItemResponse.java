package instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import instamart.api.responses.BaseResponseObject;

import java.util.List;

public final class FavoritesSkuListItemResponse extends BaseResponseObject {

    @JsonProperty(value = "products_sku")
    private List<Integer> productsSkuList;

    public List<Integer> getProductsSkuList() {
        return productsSkuList;
    }
}
