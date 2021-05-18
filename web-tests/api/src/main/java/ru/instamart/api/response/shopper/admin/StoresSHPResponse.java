package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.StoreV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class StoresSHPResponse extends BaseResponseObject {
    private List<StoreV1> stores = null;
    private MetaV1 meta;
}
