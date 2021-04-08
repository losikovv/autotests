package ru.instamart.api.responses.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public final class FavoritesSkuListItemV2Response extends BaseResponseObject {
    @JsonProperty(value = "products_sku")
    private List<Integer> productsSkuList;
}
