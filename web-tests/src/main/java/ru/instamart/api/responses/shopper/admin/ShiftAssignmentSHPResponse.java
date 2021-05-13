package ru.instamart.api.responses.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.shopper.admin.ShiftAssignmentV1;
import ru.instamart.api.responses.BaseResponseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftAssignmentSHPResponse extends BaseResponseObject {
    private ShiftAssignmentV1 shiftAssignment;
}
