package instamart.api.responses.shopper;

import instamart.api.responses.BaseResponseObject;
import instamart.api.objects.shopper.ShopperData;
import instamart.api.objects.shopper.ShopperIncluded;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperResponse extends BaseResponseObject {
    private ShopperData data;
    private List<ShopperIncluded> included = null;
}
