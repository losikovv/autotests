package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.ShopperV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShoppersSHPResponse extends BaseResponseObject {
    private List<ShopperV1> shoppers = null;
    private MetaV1 meta;
}
