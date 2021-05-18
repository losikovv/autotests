package ru.instamart.api.response.shopper.app;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.app.OperationShiftSHP;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class OperationShiftSHPResponse extends BaseResponseObject {
    private OperationShiftSHP operationShift;
}
