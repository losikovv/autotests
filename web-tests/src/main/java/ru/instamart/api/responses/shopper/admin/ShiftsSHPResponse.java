package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.MetaV1;
import ru.instamart.api.objects.shopper.admin.ShiftV1;
import ru.instamart.api.responses.BaseResponseObject;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftsSHPResponse extends BaseResponseObject {
    private List<ShiftV1> shifts = null;
    private MetaV1 meta;
}
