package ru.instamart.api.responses.shopper;

import ru.instamart.api.objects.shopper.OperationShift;
import ru.instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationShiftResponse extends BaseResponseObject {
    private OperationShift operationShift;
}
