package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.ShopperDataSHP;
import ru.instamart.api.model.shopper.app.ShopperIncludedSHP;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShopperSHPResponse extends BaseResponseObject {
    private ShopperDataSHP data;
    private List<ShopperIncludedSHP> included = null;
}
