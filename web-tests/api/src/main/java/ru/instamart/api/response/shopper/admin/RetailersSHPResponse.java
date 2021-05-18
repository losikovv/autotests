package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.RetailerV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class RetailersSHPResponse extends BaseResponseObject {
    private List<RetailerV1> retailers = null;
    private MetaV1 meta;
}
