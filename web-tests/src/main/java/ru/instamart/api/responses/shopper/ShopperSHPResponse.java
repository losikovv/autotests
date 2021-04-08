package ru.instamart.api.responses.shopper;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.ShopperDataSHP;
import ru.instamart.api.objects.shopper.ShopperIncludedSHP;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperSHPResponse extends BaseResponseObject {
    private ShopperDataSHP data;
    private List<ShopperIncludedSHP> included = null;
}
