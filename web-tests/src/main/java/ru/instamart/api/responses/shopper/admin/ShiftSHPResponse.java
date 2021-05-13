package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.ShiftV1;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftSHPResponse extends BaseResponseObject {
    private ShiftV1 shift;
}
