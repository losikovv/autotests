package ru.instamart.api.response.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.model.shopper.admin.ShiftAssignmentV1;
import ru.instamart.api.response.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftAssignmentSHPResponse extends BaseResponseObject {
    private ShiftAssignmentV1 shiftAssignment;
}
