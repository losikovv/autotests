package instamart.api.responses.shopper;

import instamart.api.objects.shopper.OperationShift;
import instamart.api.responses.BaseResponseObject;

public class OperationShiftResponse extends BaseResponseObject {

    private OperationShift operationShift;

    public OperationShift getOperationShift() {
        return operationShift;
    }

    public void setOperationShift(OperationShift operationShift) {
        this.operationShift = operationShift;
    }

}
