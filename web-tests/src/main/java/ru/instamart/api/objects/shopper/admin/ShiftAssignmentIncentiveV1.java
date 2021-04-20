package ru.instamart.api.objects.shopper.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.instamart.api.objects.BaseObject;

@Data
@EqualsAndHashCode(callSuper=false)
public class ShiftAssignmentIncentiveV1 extends BaseObject {
    private Integer id;
    private Integer shiftAssignmentId;
    private Integer amount;
    private String description;
    private String createdBy;
    private String createdAt;
}
