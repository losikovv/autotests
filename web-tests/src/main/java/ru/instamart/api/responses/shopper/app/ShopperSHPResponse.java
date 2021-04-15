package ru.instamart.api.responses.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.app.ShopperDataSHP;
import ru.instamart.api.objects.shopper.app.ShopperIncludedSHP;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperSHPResponse extends BaseResponseObject {
    private ShopperDataSHP data;
    private List<ShopperIncludedSHP> included = null;
}
