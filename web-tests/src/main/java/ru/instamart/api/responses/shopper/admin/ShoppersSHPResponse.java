package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.MetaV1;
import ru.instamart.api.objects.shopper.admin.ShopperV1;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShoppersSHPResponse extends BaseResponseObject {
    private List<ShopperV1> shoppers = null;
    private MetaV1 meta;
}
