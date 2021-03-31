package instamart.api.responses.shopper;

import instamart.api.objects.shopper.OperationShift;
import instamart.api.responses.BaseResponseObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationShiftResponse extends BaseResponseObject {
    private OperationShift operationShift;
}
