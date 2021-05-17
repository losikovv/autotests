package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.MetaV1;
import ru.instamart.api.model.shopper.admin.ShiftV1;
import ru.instamart.api.response.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftsSHPResponse extends BaseResponseObject {
    private List<ShiftV1> shifts = null;
    private MetaV1 meta;
}
